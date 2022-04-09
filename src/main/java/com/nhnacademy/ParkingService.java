package com.nhnacademy;

import java.util.ArrayList;
import java.util.List;

public class ParkingService{
    private List<String> parkArea = new ArrayList<>();
    private List<String> entry = new ArrayList<>();
    private List<String> exit = new ArrayList<>();
    public int carCome(Car car) {
        if (car.getType() == Type.대형) {
            throw new CarTypeTooBigException("To Big Type Car");
        }
        return car.getCarNum();
    }

    public List<String> getParkArea() {
        return this.parkArea;
    }

    public void carParking(Car car) {
        if (!parkArea.contains(car.getParkingArea())) {
            parkArea.add(car.getParkingArea());
        }
    }

    public boolean carOut(Car car) {
        ParkingPay parkingPay = new ParkingPay();
        if(parkingPay.parkingPay(car)) {
            return true;
        }
        throw new NoMoneyException("Not enough money");
    }

    public boolean changeCarOut(Car car) {
        ParkingPay parkingPay = new ParkingPay();
        if(parkingPay.changeParkingPay(car)) {
            return true;
        }
        throw new NoMoneyException("Not enough money");
    }

    public void constructParkLotEntry() {
        entry.add("A");
        entry.add("B");
    }

    public void constructParkLotExit() {
        exit.add("A");
        exit.add("B");
        exit.add("B");
    }

    public boolean barcodeReading(Car car) {
        Payco payco = new Payco();
        boolean access = payco.access(car.getOwner());
        return access;
    }

    public List<String> getEntry() {
        return entry;
    }

    public List<String> getExit() {
        return exit;
    }
}
