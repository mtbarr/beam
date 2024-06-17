package io.github.mtbarr.beam.redis;

import io.github.mtbarr.beam.core.BeamMessageBroker;
import io.github.mtbarr.beam.core.adapter.MessageAdapter;
import io.github.mtbarr.beam.core.adapter.MessageAdapterContainer;
import io.github.mtbarr.beam.core.adapter.exception.MessageAdapterNotFoundException;
import io.github.mtbarr.beam.core.io.ByteArrayReader;
import io.github.mtbarr.beam.core.io.ByteArrayWriter;
import io.github.mtbarr.beam.core.message.Message;
import io.github.mtbarr.beam.core.subscriber.MessageSubscriber;
import io.github.mtbarr.beam.redis.source.RedisSource;
import org.jetbrains.annotations.NotNull;
import redis.clients.jedis.BinaryJedisPubSub;
import redis.clients.jedis.Jedis;

import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.concurrent.ExecutorService;

public class RedisMessageBroker extends BeamMessageBroker {

    private final RedisSource redisSource;
    private final ExecutorService executorService;

    private final Map<String, Map<Class<?>, InternalPubSub<?>>> localRedisSubscribers = new LinkedHashMap<>();

    public RedisMessageBroker(@NotNull RedisSource redisSource, @NotNull ExecutorService executorService) {
        this.redisSource = redisSource;
        this.executorService = executorService;
    }

    @Override
    public void publishMessage(@NotNull String subscriber, @NotNull Message message) {
        executorService.submit(() -> {
            try (Jedis jedis = redisSource.getJedisInstance()) {
                ByteArrayWriter writer = ByteArrayWriter.newByteArrayWriter();

                /*
                 * The header of the message must be the class name of the message.
                 */
                writer.writeString(message.getClass().getName());

                MessageAdapter<Message> adapter = messageAdapterContainer.getMessageAdapter(Message.class);
                if (adapter == null) {
                    throw new MessageAdapterNotFoundException("No adapter found for message class: " + message.getClass().getName());
                }

                adapter.encode(message, writer);

                jedis.publish(subscriber.getBytes(), writer.toByteArray());
            }
        });
    }

    @Override
    public <M extends Message> void subscribe(@NotNull String subscriberId, @NotNull Class<M> clazz, @NotNull MessageSubscriber<M> subscriber) {
        try (Jedis jedis = redisSource.getJedisInstance()) {
            InternalPubSub<M> internalPubSub = new InternalPubSub<>(subscriberId, clazz, messageAdapterContainer);
            addNewLocalRedisSubscriber(subscriberId, internalPubSub);
            jedis.subscribe(internalPubSub, subscriberId.getBytes());
        }
    }

    @Override
    public <M extends Message> void unsubscribe(@NotNull String subscriberId, Class<M> clazz) {
        removeLocalRedisSubscriber(subscriberId, clazz);
    }

    private void addNewLocalRedisSubscriber(@NotNull String subscriberId, @NotNull InternalPubSub<?> subscriber) {
        Map<Class<?>, InternalPubSub<?>> subscribers = localRedisSubscribers.computeIfAbsent(subscriberId, k -> new LinkedHashMap<>());
        subscribers.put(subscriber.messageClazz, subscriber);
    }

    private void removeLocalRedisSubscriber(@NotNull String subscriberId, @NotNull Class<?> clazz) {
        Map<Class<?>, InternalPubSub<?>> subscribers = getLocalRedisSubscribers(subscriberId);
        InternalPubSub<?> subscriber = subscribers.get(clazz);
        if (subscriber != null) {
            subscriber.unsubscribe();
            subscribers.remove(clazz);
        }
    }

    private Map<Class<?>, InternalPubSub<?>> getLocalRedisSubscribers(@NotNull String subscriberId) {
        Map<Class<?>, InternalPubSub<?>> map = localRedisSubscribers.get(subscriberId);
        return map == null ? Collections.emptyMap() : map;
    }

    private void clearSubscribers(@NotNull String subscriberId) {
        Map<Class<?>, InternalPubSub<?>> removed = localRedisSubscribers.remove(subscriberId);
        if (removed != null) {
            removed.values().forEach(BinaryJedisPubSub::unsubscribe);
        }
    }

    class InternalPubSub<M extends Message> extends BinaryJedisPubSub {

        private final String subscriberId;
        private final Class<M> messageClazz;
        private final MessageAdapterContainer adapterContainer;

        public InternalPubSub(@NotNull String subscriberId, @NotNull Class<M> messageClazz, @NotNull MessageAdapterContainer adapterContainer) {
            this.subscriberId = subscriberId;
            this.messageClazz = messageClazz;
            this.adapterContainer = adapterContainer;
        }


        @SuppressWarnings("unchecked")
        @Override
        public void onMessage(byte[] channel, byte[] message) {
            byte[] subscriberIdBytes = subscriberId.getBytes();
            if (!Arrays.equals(channel, subscriberIdBytes)) {
                return;
            }

            ByteArrayReader reader = ByteArrayReader.read(message);
            /*
             * The header of the message must be the class name of the message.
             */
            String className = reader.readString();
            Class<?> clazz = getMessageClass(className);
            if (clazz == null || !messageClazz.isAssignableFrom(clazz)) {
                return;
            }

            MessageAdapter<M> adapter = adapterContainer.getMessageAdapter(messageClazz);
            if (adapter == null) {
                throw new MessageAdapterNotFoundException("No adapter found for message class: " + messageClazz.getName());
            }

            MessageSubscriber<M> subscriber = (MessageSubscriber<M>) getSubscribers(subscriberId).get(messageClazz);
            if (subscriber != null) {
                M decodedMessage = adapter.decode(reader);
                subscriber.onMessageReceived(decodedMessage);
            }
        }
    }
}
