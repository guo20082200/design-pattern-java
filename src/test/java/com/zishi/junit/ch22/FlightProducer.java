package com.zishi.junit.ch22;


import javax.enterprise.inject.Produces;
import java.io.IOException;

public class FlightProducer {

    @Produces
    @FlightNumber(number= "AA1234")
    public Flight createFlight() throws IOException {
        return FlightBuilderUtil.buildFlightFromCsv();
    }
}