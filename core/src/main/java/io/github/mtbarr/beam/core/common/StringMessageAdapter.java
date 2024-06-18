package io.github.mtbarr.beam.core.common;

import io.github.mtbarr.beam.core.adapter.MessageAdapter;
import io.github.mtbarr.beam.core.adapter.exception.MessageEncoderException;
import io.github.mtbarr.beam.core.io.ByteArrayReader;
import io.github.mtbarr.beam.core.io.ByteArrayWriter;

public class StringMessageAdapter extends MessageAdapter<StringMessage> {

    public StringMessageAdapter() {
        super(StringMessage.class);
    }

    @Override
    public StringMessage decode(ByteArrayReader reader) throws MessageEncoderException {
        String id = reader.readString();
        String content = reader.readString();
        return new StringMessage(id, content);
    }

    @Override
    public void encode(StringMessage message, ByteArrayWriter writer) throws MessageEncoderException {
        writer.writeString(message.getId());
        writer.writeString(message.getContent());
    }
}
