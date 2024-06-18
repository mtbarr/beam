package io.github.mtbarr.beam.core.adapter;

import io.github.mtbarr.beam.core.common.StringMessage;
import io.github.mtbarr.beam.core.common.StringMessageAdapter;
import io.github.mtbarr.beam.core.message.Message;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * This class is named MessageAdapterContainer. It is designed to manage MessageAdapters.
 * It uses a map for storing adapters where the key is the class of the message and the value is the adapter.
 */
public class MessageAdapterContainer {

    private final Map<Class<?>, MessageAdapter<?>> messageAdapters = new LinkedHashMap<>() {{
        put(StringMessage.class, new StringMessageAdapter());
    }};

    /**
     * This method is used to add a new adapter to the map.
     *
     * @param messageAdapter The adapter to be added.
     * @param <M> The type of the message. It must extend Message.
     */
    public <M extends Message> void addMessageAdapter(@NotNull MessageAdapter<M> messageAdapter) {
        messageAdapters.put(messageAdapter.getMessageClass(), messageAdapter);
    }


    @SuppressWarnings("unchecked")
    @Nullable
    public <M extends Message> MessageAdapter<M> getMessageAdapter(@NotNull Class<M> messageClass) {
        MessageAdapter<?> messageAdapter = messageAdapters.get(messageClass);
        if (messageAdapter == null) return null;

        return (MessageAdapter<M>) messageAdapter;
    }


    public void removeMessageAdapter(@NotNull Class<?> messageClass) {
        messageAdapters.remove(messageClass);
    }
}