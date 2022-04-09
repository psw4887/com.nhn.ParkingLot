package com.nhnacademy;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class ParkingServiceTest {
    private ParkingService service;

    @BeforeEach
    void setUp() {
        service = new ParkingService();
    }

    @DisplayName("주차장에 차가 들어온다.")
    @Test
    void car_inParkingLot() {
        Car car = new Car(2065, "A-1");
        int result = service.carCome(car);
        assertThat(result)
            .isEqualTo(2065);
    }

    @DisplayName("A-1에 주차한다")
    @Test
    void car_parkCorrectArea() {
        Car car = new Car(2065, "A-1");
        service.carParking(car);
        assertThat(service.getParkArea().contains("A-1"))
            .isTrue();
    }

    @DisplayName("주차장에서 차가 나간다. 차가 나갈려면 주차 시간만큼 결제를 해야한다.")
    @Test
    void car_outPark_payForTime_ifZeroMoneyNoOut() {
        User user = new User("박세완", 1000);
        Car car = new Car(user, 2065, "A-1");
        car.setParkingTime(30);
        assertThat(service.carOut(car))
            .isTrue();
        car.setParkingTime(31);
        assertThatThrownBy(() -> service.carOut(car))
            .isInstanceOf(NoMoneyException.class)
            .hasMessageContaining("Not enough money");
        car.setParkingTime(61);
        user.setMoney(3000);
        assertThat(service.carOut(car))
            .isTrue();
        car.setParkingTime(2880);
        user.setMoney(20000);
        assertThat(service.carOut(car))
            .isTrue();
    }

    @DisplayName("주차장 입구가 n개, 주차장 출구도 n개 입니다.")
    @Test
    void multipleEntryAndExit() {
        service.constructParkLotEntry();
        service.constructParkLotExit();
        assertThat(service.getEntry().size())
            .isEqualTo(2);
        assertThat(service.getExit().size())
            .isEqualTo(3);
    }

    @DisplayName("요금표가 변경됐습니다.")
    @Test
    void changeParkingPay() {
        User user = new User("박세완", 0);
        Car car = new Car(user, 2065, "A-1");
        car.setParkingTime(30);
        assertThat(service.changeCarOut(car))
            .isTrue();
        car.setParkingTime(31);
        assertThatThrownBy(() -> service.changeCarOut(car))
            .isInstanceOf(NoMoneyException.class)
            .hasMessageContaining("Not enough money");
        car.setParkingTime(60);
        car.setType(Type.경량);
        user.setMoney(500);
        assertThat(service.changeCarOut(car))
            .isTrue();
        car.setParkingTime(2880);
        car.setType(Type.중형);
        user.setMoney(30000);
        assertThat(service.changeCarOut(car))
            .isTrue();
    }

    @DisplayName("대형차는 주차할 수 없습니다.")
    @Test
    void cant_parkForBig() {
        Car car = new Car(2034,"A-1");
        car.setType(Type.대형);
        assertThatThrownBy(() -> service.carCome(car))
            .isInstanceOf(CarTypeTooBigException.class)
            .hasMessageContaining("To Big Type Car");
    }

    @DisplayName("사용자(User)가 Payco 회원인 경우에는 주차 요금이 10% 할인됩니다.")
    @Test
    void if_userIsVip_discount10Percent() {
        User user = new User("박세완", 13500);
        Car car = new Car(user, 1440);
        user.setPaycoAccount(true);
        assertThat(service.barcodeReading(car))
            .isTrue();
        assertThat(service.changeCarOut(car))
            .isTrue();
        user.setPaycoAccount(false);
        assertThat(service.barcodeReading(car))
            .isFalse();
        assertThatThrownBy(() -> service.changeCarOut(car))
            .isInstanceOf(NoMoneyException.class)
            .hasMessageContaining("Not enough money");
    }

    @DisplayName("시간 주차권이 존재합니다.")
    @Test
    void available_parkingCouponForTime() {
        User user = new User("박세완", 1000);
        Car car = new Car(user, 180);
        user.setCoupon(Coupon.TWOHOUR);
        assertThat(service.changeCarOut(car))
            .isTrue();
        user.setCoupon(Coupon.ONEHOUR);
        user.setMoney(0);
        car.setParkingTime(59);
        assertThat(service.changeCarOut(car))
            .isTrue();
        car.setParkingTime(60);
        assertThat(service.changeCarOut(car))
            .isTrue();
        car.setParkingTime(121);
        assertThatThrownBy(() -> service.changeCarOut(car))
            .isInstanceOf(NoMoneyException.class)
            .hasMessageContaining("Not enough money");
    }
}