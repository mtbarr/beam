package io.github.mtbarr.beam.core.message;

import org.jetbrains.annotations.NotNull;

public abstract class AbstractMessage implements Message {

    private final String id;

    public AbstractMessage(@NotNull String id) {
        this.id = id;
    }

    @Override
    public String getId() {
        return id;
    }
}
