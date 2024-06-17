package io.github.mtbarr.beam.core;

import io.github.mtbarr.beam.core.adapter.MessageAdapter;
import io.github.mtbarr.beam.core.adapter.MessageAdapterContainer;
import io.github.mtbarr.beam.core.message.Message;
import io.github.mtbarr.beam.core.subscriber.MessageSubscriber;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

public abstract class BeamMessageBroker {


    /**
     * A fast lookup map for class to name. to avoid Class#forName calls.
     */
    private final Map<String, Class<?>> nameToClassMap = new HashMap<>();

    protected final MessageAdapterContainer messageAdapterContainer = new MessageAdapterContainer();
    private final Map<String, Map<Class<?>, MessageSubscriber<?>>> subscribers = new HashMap<>();


    public <T extends Message> void registerMessageAdapter(@NotNull MessageAdapter<T> messageAdapter) {
        messageAdapterContainer.addMessageAdapter(messageAdapter);

        Class<?> messageClass = messageAdapter.getMessageClass();
        registerMessageClass(messageClass);
    }


    public void unregisterMessageAdapter(@NotNull MessageAdapter<?> messageAdapter) {
        messageAdapterContainer.removeMessageAdapter(messageAdapter.getMessageClass());
    }


    @Nullable
    public <T extends Message> MessageAdapter<T> getMessageAdapter(@NotNull Class<T> messageClass) {
        return messageAdapterContainer.getMessageAdapter(messageClass);
    }


    public void removeMessageAdapter(@NotNull Class<?> messageClass) {
        messageAdapterContainer.removeMessageAdapter(messageClass);
    }


    public abstract void publishMessage(@NotNull String subscriber, @NotNull Message message);

    public abstract <M extends Message> void subscribe(@NotNull String subscriberId, Class<M> clazz, @NotNull MessageSubscriber<M> subscriber);

    public abstract <M extends Message> void unsubscribe(@NotNull String subscriberId, Class<M> clazz);

    protected void registerMessageClass(@NotNull Class<?> clazz) {
        nameToClassMap.put(clazz.getName(), clazz);
    }

    @Nullable
    protected Class<?> getMessageClass(@NotNull String name) {
        return nameToClassMap.get(name);
    }

    protected Map<Class<?>, MessageSubscriber<?>> getSubscribers(@NotNull String subscriberId) {
        Map<Class<?>, MessageSubscriber<?>> map = subscribers.get(subscriberId);
        return map == null ? Collections.emptyMap() : map;
    }

    protected <M extends Message> void registerNewSubscriber(@NotNull String subscriberId, Class<M> clazz, @NotNull MessageSubscriber<M> subscriber) {
        Map<Class<?>, MessageSubscriber<?>> map = getSubscribers(subscriberId);
        if (map.isEmpty()) {
            map = new LinkedHashMap<>();
        }

        map.put(clazz, subscriber);
        subscribers.put(subscriberId, map);
    }


    protected <M extends Message> void unregisterSubscriber(@NotNull String subscriberId, Class<M> clazz) {
        Map<Class<?>, MessageSubscriber<?>> map = subscribers.get(subscriberId);
        if (map.isEmpty()) {
            return;
        }

        map.remove(clazz);
    }


    public void unregisterAllSubscribers(@NotNull String subscriberId) {
        subscribers.remove(subscriberId);
    }

    @SuppressWarnings("unchecked")
    @Nullable
    protected <M extends Message> MessageSubscriber<M> getSubscriber(@NotNull String subscriberId, @NotNull Class<M> clazz) {
        Map<Class<?>, MessageSubscriber<?>> map = subscribers.get(subscriberId);
        if (map == null) {
            return null;
        }

        MessageSubscriber<?> subscriber = map.get(clazz);
        if (subscriber == null) {
            return null;
        }

        return (MessageSubscriber<M>) subscriber;
    }
}