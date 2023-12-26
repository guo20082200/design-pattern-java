package com.zishi.junit.ch22;


import javax.enterprise.inject.Produces;
import java.io.IOException;

public class FlightProducer {
    @Produces
    public Flight createFlight() throws IOException {
        return FlightBuilderUtil.buildFlightFromCsv();
    }
}