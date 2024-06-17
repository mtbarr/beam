package io.github.mtbarr.beam.core.adapter.exception;

public class MessageDecoderException extends RuntimeException {
    public MessageDecoderException() {
    }

    public MessageDecoderException(String message) {
        super(message);
    }

    public MessageDecoderException(String message, Throwable cause) {
        super(message, cause);
    }

    public MessageDecoderException(Throwable cause) {
        super(cause);
    }

    public MessageDecoderException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
