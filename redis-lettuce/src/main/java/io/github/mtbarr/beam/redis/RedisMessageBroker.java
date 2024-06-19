package io.github.mtbarr.beam.redis;

import io.github.mtbarr.beam.core.BeamMessageBroker;
import io.github.mtbarr.beam.core.adapter.MessageAdapter;
import io.github.mtbarr.beam.core.adapter.exception.MessageAdapterNotFoundException;
import io.github.mtbarr.beam.core.io.ByteArrayReader;
import io.github.mtbarr.beam.core.io.ByteArrayWriter;
import io.github.mtbarr.beam.core.message.Message;
import io.github.mtbarr.beam.core.subscriber.MessageSubscriber;
import io.github.mtbarr.beam.redis.source.RedisSource;
import io.lettuce.core.RedisClient;
import io.lettuce.core.codec.ByteArrayCodec;
import io.lettuce.core.pubsub.RedisPubSubAdapter;
import io.lettuce.core.pubsub.StatefulRedisPubSubConnection;
import org.jetbrains.annotations.NotNull;

import java.util.Arrays;


public class RedisMessageBroker extends BeamMessageBroker {

    private final RedisSource redisSource;

    public RedisMessageBroker(RedisSource redisSource) {
        this.redisSource = redisSource;
    }

    @Override
    public void publishMessage(@NotNull String subscriber, @NotNull Message message) {
        RedisClient client = redisSource.getClient();
        StatefulRedisPubSubConnection<byte[], byte[]> stringStringStatefulRedisPubSubConnection = client.connectPubSub(ByteArrayCodec.INSTANCE);

        try (ByteArrayWriter writer = ByteArrayWriter.newByteArrayWriter()) {
            writer.writeString(message.getClass().getName());

            MessageAdapter<Message> messageAdapter = getMessageAdapter(Message.class);
            if (messageAdapter == null) {
                throw new MessageAdapterNotFoundException("Message adapter not found for class: " + Message.class.getName());
            }

            messageAdapter.encode(message, writer);
            stringStringStatefulRedisPubSubConnection.async().publish(subscriber.getBytes(), writer.toByteArray());
        }
    }

    @Override
    public <M extends Message> void subscribe(
      @NotNull String subscriberId,
      Class<M> clazz,
      @NotNull MessageSubscriber<M> subscriber
    ) {

        RedisClient client = redisSource.getClient();

        StatefulRedisPubSubConnection<byte[], byte[]> pubSubConnection = client.connectPubSub(ByteArrayCodec.INSTANCE);
        pubSubConnection.addListener(new InternalPubSubListener<>(clazz, subscriberId));
        pubSubConnection.async().subscribe(subscriberId.getBytes());
    }

    @Override
    public <M extends Message> void unsubscribe(@NotNull String subscriberId, Class<M> clazz) {
        RedisClient client = redisSource.getClient();
        StatefulRedisPubSubConnection<byte[], byte[]> pubSubConnection = client.connectPubSub(ByteArrayCodec.INSTANCE);
        pubSubConnection.async().unsubscribe(subscriberId.getBytes());
    }

    class InternalPubSubListener<M extends Message> extends RedisPubSubAdapter<byte[], byte[]> {

        private final Class<M> clazz;
        private final String subscriberId;

        public InternalPubSubListener(@NotNull Class<M> clazz, @NotNull String subscriberId) {
            this.clazz = clazz;
            this.subscriberId = subscriberId;
        }

        @Override
        public void message(byte[] channel, byte[] message) {
            if (!Arrays.equals(channel, subscriberId.getBytes())) {
                return;
            }

            try (ByteArrayReader byteArrayReader = ByteArrayReader.read(message)) {
                String className = byteArrayReader.readString();
                if (!clazz.getName().equals(className)) {
                    return;
                }

                MessageSubscriber<M> subscriber = getSubscriber(subscriberId, clazz);
                if (subscriber == null) {
                    return;
                }

                MessageAdapter<M> messageAdapter = getMessageAdapter(clazz);
                if (messageAdapter == null) {
                    throw new MessageAdapterNotFoundException("Message adapter not found for class: " + clazz.getName());
                }

                M decoded = messageAdapter.decode(byteArrayReader);
                subscriber.onMessageReceived(decoded);
            }
        }
    }
}

