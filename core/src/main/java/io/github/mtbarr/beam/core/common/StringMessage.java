package io.github.mtbarr.beam.core.common;

import io.github.mtbarr.beam.core.message.AbstractMessage;
import org.jetbrains.annotations.NotNull;

public class StringMessage extends AbstractMessage {

    private final String content;

    public StringMessage(@NotNull String id, @NotNull String content) {
        super(id);
        this.content = content;
    }

    public String getContent() {
        return content;
    }
}
