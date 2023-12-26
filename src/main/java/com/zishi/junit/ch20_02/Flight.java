package com.zishi.junit.ch20_02;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public abstract class Flight {
    private String id;
    //List<Passenger> passengers = new ArrayList<Passenger>();
    Set<Passenger> passengers = new HashSet<>();

    public Flight(String id) {
        this.id = id;
    }

    public Set<Passenger> getPassengersSet() {
        return Collections.unmodifiableSet(passengers);
    }

    public String getId() {
        return id;
    }


    public abstract boolean addPassenger(Passenger passenger);

    public abstract boolean removePassenger(Passenger passenger);
}