package com.manning.junitbook.airport;

import com.zishi.junit.ch20_02.Passenger;

import java.util.HashMap;
import java.util.Map;

public class Mileage {

    public static final int VIP_FACTOR = 10;
    public static final int REGULAR_FACTOR = 20;
    private Map<Passenger, Integer> passengersMileageMap = new HashMap<>();
    private Map<Passenger, Integer> passengersPointsMap = new HashMap<>();


    public void addMileage(Passenger passenger, int miles) {
        if (passengersMileageMap.containsKey(passenger)) {
            passengersMileageMap.put(passenger,
                    passengersMileageMap.get(passenger) + miles);
        } else {
            passengersMileageMap.put(passenger, miles);
        }
    }

    public void calculateGivenPoints() {
        for (Passenger passenger : passengersMileageMap.keySet()) {
            if (passenger.isVip()) {
                passengersPointsMap.put(passenger,
                        passengersMileageMap.get(passenger) / VIP_FACTOR);
            } else {
                passengersPointsMap.put(passenger,
                        passengersMileageMap.get(passenger) / REGULAR_FACTOR);
            }
        }
    }

    public Map<Passenger, Integer> getPassengersPointsMap() {
        return passengersPointsMap;
    }
}
