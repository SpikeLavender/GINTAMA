package com.natsumes.wezard.exception;

public class NatsumeException extends RuntimeException {
    private static final long serialVersionUID = 1624888731846654082L;

    public NatsumeException() {

    }

    public NatsumeException(String message) {
        super(message);
    }

    public NatsumeException(String message, Throwable cause) {
        super(message, cause);
    }

    public NatsumeException(Throwable cause) {
        super(cause);
    }
}
