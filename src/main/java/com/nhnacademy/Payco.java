package com.nhnacademy;

public class Payco {
    public boolean access(User owner) {
        if(owner.isPaycoAccount()) {
            return true;
        }
        return false;
    }
}
