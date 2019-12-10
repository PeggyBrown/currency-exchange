package com.exchange.app;

public class CurrencyNotSupportedException extends RuntimeException {
    private String message;

    public CurrencyNotSupportedException(String message) {
        this.message = message;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
