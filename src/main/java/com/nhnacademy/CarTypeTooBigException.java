package com.nhnacademy;

public class CarTypeTooBigException extends RuntimeException {
    public CarTypeTooBigException(String msg) {
        super(msg);
    }
}
