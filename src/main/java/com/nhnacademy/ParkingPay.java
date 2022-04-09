package com.nhnacademy;

import java.math.BigDecimal;

public class ParkingPay {
    private ParkingService parkingService = new ParkingService();
    public boolean parkingPay(Car car) {
        int parkingPay = calculateParkingPay(car);
        if (car.getParkingMinute() > 1440) {
            int time = car.getParkingMinute() - 1440;
            car.setParkingTime(time);
            parkingPay += calculateParkingPay(car);
        }
        return car.getOwner().getMoney() >= parkingPay;
    }

    private int calculateParkingPay(Car car) {
        double addPay = 500;
        if (car.getParkingMinute() <= 30) {
            return 1000;
        }
        double carParkTime = Math.ceil((car.getParkingMinute()-30)/10.0);
        int parkingPay = (1000 + (int)(addPay*carParkTime));
        if (parkingPay > 10000) {
            parkingPay = 10000;
        }
        return parkingPay;
    }

    public boolean changeParkingPay(Car car) {
        if (car.getOwner().getCoupon()!=null) {
            car.setParkingTime(car.getOwner().useCoupon(car.getParkingMinute()));
        }
        if (car.getParkingMinute() < 0) {
            car.setParkingTime(0);
        }
        BigDecimal parkingPay = calculateChangeParkingPay(car);

        if (car.getParkingMinute() > 1440) {
            int time = car.getParkingMinute()-1440;
            car.setParkingTime(time);
            parkingPay = parkingPay.add(calculateChangeParkingPay(car));
        }
        if (parkingService.barcodeReading(car)) {
            parkingPay = parkingPay.multiply(BigDecimal.valueOf(0.9));
        }
        if (car.getType() == Type.경량)
            return BigDecimal.valueOf(car.getOwner().getMoney())
                .compareTo((parkingPay.divide(BigDecimal.valueOf(2))).subtract(BigDecimal.valueOf(1))) > 0;
        return BigDecimal.valueOf(car.getOwner().getMoney()).compareTo(parkingPay.subtract(BigDecimal.valueOf(1))) > 0;
    }

    private BigDecimal calculateChangeParkingPay(Car car) {
        BigDecimal addPay = BigDecimal.valueOf(500);
        if (car.getParkingMinute() <= 30) {
            return BigDecimal.valueOf(0);
        }
        if (car.getParkingMinute() <= 60) {
            return BigDecimal.valueOf(1000);
        }
        double carParkTime = Math.ceil((car.getParkingMinute()-60)/10.0);
        BigDecimal parkingPay = addPay.multiply(BigDecimal.valueOf(carParkTime)).add(BigDecimal.valueOf(1000));
        if (parkingPay.compareTo(BigDecimal.valueOf(15000))>0) {
            parkingPay = BigDecimal.valueOf(15000);
        }
        return parkingPay;
    }
}
