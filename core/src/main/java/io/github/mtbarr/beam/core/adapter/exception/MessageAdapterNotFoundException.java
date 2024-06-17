package io.github.mtbarr.beam.core.adapter.exception;

public class MessageAdapterNotFoundException extends RuntimeException {

    public MessageAdapterNotFoundException() {
    }

    public MessageAdapterNotFoundException(String message) {
        super(message);
    }

    public MessageAdapterNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    public MessageAdapterNotFoundException(Throwable cause) {
        super(cause);
    }

    public MessageAdapterNotFoundException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
