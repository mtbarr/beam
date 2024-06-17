package io.github.mtbarr.beam.core.subscriber;

import io.github.mtbarr.beam.core.message.Message;
import org.jetbrains.annotations.NotNull;


@FunctionalInterface
public interface MessageSubscriber<M extends Message> {

    /**
     * Called when a message is received.
     *
     * @param message The message that was received.
     */
    void onMessageReceived(@NotNull M message);
}