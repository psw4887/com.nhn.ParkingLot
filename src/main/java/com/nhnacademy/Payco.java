package com.nhnacademy;

public class Payco {
    public boolean access(User owner) {
        return owner.isPaycoAccount();
    }
}
