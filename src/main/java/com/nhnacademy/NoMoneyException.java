package com.nhnacademy;

public class NoMoneyException extends RuntimeException {
    public NoMoneyException(String msg) {
        super(msg);
    }
}
