package com.natsumes.wezard.exception;

public class ExceptionFactory {

    private ExceptionFactory() {

    }

    public static RuntimeException wrapException(String message, Exception e) {
        return new NatsumeException(e.getClass().getSimpleName() + ": " + message, e);
    }

}
