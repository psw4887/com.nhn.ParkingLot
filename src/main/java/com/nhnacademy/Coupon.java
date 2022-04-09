package com.nhnacademy;

public enum Coupon implements Couponable{
    ONEHOUR() {
        @Override
        public int useCoupon(int time) {
            return time-60;
        }
    },
    TWOHOUR() {
        @Override
        public int useCoupon(int time) {
            return time-120;
        }
    }
}
