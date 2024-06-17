package io.github.mtbarr.beam.core.adapter.exception;

public class MessageEncoderException extends RuntimeException {

    public MessageEncoderException() {
    }

    public MessageEncoderException(String message) {
        super(message);
    }

    public MessageEncoderException(String message, Throwable cause) {
        super(message, cause);
    }

    public MessageEncoderException(Throwable cause) {
        super(cause);
    }

    public MessageEncoderException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
