package io.github.mtbarr.beam.core.adapter;

import io.github.mtbarr.beam.core.adapter.exception.MessageEncoderException;
import io.github.mtbarr.beam.core.io.ByteArrayReader;
import io.github.mtbarr.beam.core.io.ByteArrayWriter;
import io.github.mtbarr.beam.core.message.Message;

/**
 * Represents a message adapter.
 * you can use this to convert a message to a specific type, and vice versa.
 *
 * @param <M> the message type.
 * @author mtbarr
 */
public abstract class MessageAdapter<M extends Message> {

    private final Class<M> messageClass;

    public MessageAdapter(Class<M> messageClass) {
        this.messageClass = messageClass;
    }

    /**
     * Get the message class.
     *
     * @return the message class.
     */
    public Class<M> getMessageClass() {
        return messageClass;
    }

    /**
     * Decode a byte array to a message.
     *
     * @param reader the current reader being used to read the message.
     *
     * @return the message.
     * @throws MessageEncoderException if an error occurs while decoding the message.
     */
    public abstract M decode(ByteArrayReader reader) throws MessageEncoderException;

    /**
     * Encode a message to a byte array.
     *
     * @param message the message to encode.
     * @param writer the current writer being used to write the message.
     *
     * @return the byte array.
     * @throws MessageEncoderException if an error occurs while encoding the message.
     */
    public abstract void encode(M message, ByteArrayWriter writer) throws MessageEncoderException;
}
