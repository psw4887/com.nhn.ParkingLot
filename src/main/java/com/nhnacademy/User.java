package com.nhnacademy;

public class User implements Couponable{
    private String name;
    private long money;
    private boolean paycoAccount;
    private Coupon coupon;

    public User(String name, long money) {
        this.name = name;
        this.money = money;
    }

    public Coupon getCoupon() {
        return coupon;
    }

    public void setCoupon(Coupon coupon) {
        this.coupon = coupon;
    }

    public boolean isPaycoAccount() {
        return paycoAccount;
    }

    public void setPaycoAccount(boolean paycoAccount) {
        this.paycoAccount = paycoAccount;
    }

    public long getMoney() {
        return money;
    }

    public void setMoney(long money) {
        this.money = money;
    }

    @Override
    public int useCoupon(int time) {
        return coupon.useCoupon(time);
    }
}
