package com.zishi.junit.ch22;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class FlightTest {

    @Test
    public void testFlightCreation() {
        Flight flight = new Flight("AA123", 100);
        assertNotNull(flight);
    }

    @Test
    public void testInvalidFlightNumber() {
        assertThrows(RuntimeException.class,
                () -> {
                    Flight flight = new Flight("AA12", 100);
                });
        assertThrows(RuntimeException.class,
                () -> {
                    Flight flight = new Flight("AA12345", 100);
                });
    }

    @Test
    public void testValidFlightNumber() {
        Flight flight = new Flight("AA345", 100);
        assertNotNull(flight);
        flight = new Flight("AA3456", 100);
        assertNotNull(flight);
    }

    @Test
    public void testAddPassengers() {
        Flight flight = new Flight("AA1234", 50);
        flight.setOrigin("London");
        flight.setDestination("Bucharest");
        for (int i = 0; i < flight.getSeats(); i++) {
            flight.addPassenger();
        }
        assertEquals(50, flight.getPassengersNumber());
        assertThrows(RuntimeException.class, flight::addPassenger);
    }

    @Test
    public void testSetInvalidSeats() {
        Flight flight = new Flight("AA1234", 50);
        flight.setOrigin("London");
        flight.setDestination("Bucharest");
        for (int i = 0; i < flight.getSeats(); i++) {
            flight.addPassenger();
        }
        assertEquals(50, flight.getPassengersNumber());
        assertThrows(RuntimeException.class,
                () -> {
                    flight.setSeats(49);
                });
    }

    @Test
    public void testSetValidSeats() {
        Flight flight = new Flight("AA1234", 50);
        flight.setOrigin("London");
        flight.setDestination("Bucharest");
        for (int i = 0; i < flight.getSeats(); i++) {
            flight.addPassenger();
        }
        assertEquals(50, flight.getPassengersNumber());
        flight.setSeats(52);
        assertEquals(52, flight.getSeats());
    }

    @Test
    public void testChangeOrigin() {
        Flight flight = new Flight("AA1234", 50);
        flight.setOrigin("London");
        flight.setDestination("Bucharest");
        flight.takeOff();
        assertTrue(flight.isFlying());
        assertTrue(flight.isTakenOff());
        assertFalse(flight.isLanded());
        assertThrows(RuntimeException.class,
                () -> {
                    flight.setOrigin("Manchester");
                });
    }

    @Test
    public void testChangeDestination() {
        Flight flight = new Flight("AA1234", 50);
        flight.setOrigin("London");
        flight.setDestination("Bucharest");
        flight.takeOff();
        flight.land();
        assertThrows(RuntimeException.class,
                () -> {
                    flight.setDestination("Sibiu");
                });
    }

    @Test
    public void testLand() {
        Flight flight = new Flight("AA1234", 50);
        flight.setOrigin("London");
        flight.setDestination("Bucharest");
        flight.takeOff();
        assertTrue(flight.isTakenOff());
        assertFalse(flight.isLanded());
        flight.land();
        assertTrue(flight.isTakenOff());
        assertTrue(flight.isLanded());
        assertFalse(flight.isFlying());
    }
}