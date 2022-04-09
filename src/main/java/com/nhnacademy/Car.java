package com.nhnacademy;

public class Car {
    private Type type;
    private User owner;
    private int carNum;
    private String parkingArea;
    private int parkingMinute;

    public Car(int carNum, String area) {
        this.carNum = carNum;
        this.parkingArea = area;
    }

    public Car(User owner, int carNum, String area) {
        this.owner = owner;
        this.carNum = carNum;
        this.parkingArea = area;
    }

    public Car(User user, int parkingMinute) {
        this.owner = user;
        this.parkingMinute = parkingMinute;
    }

    public User getOwner() {
        return owner;
    }

    public int getCarNum() {
        return carNum;
    }

    public String getParkingArea() {
        return parkingArea;
    }

    public int getParkingMinute() {
        return parkingMinute;
    }

    public void setParkingTime(int parkingMinute) {
        this.parkingMinute = parkingMinute;
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }
}
