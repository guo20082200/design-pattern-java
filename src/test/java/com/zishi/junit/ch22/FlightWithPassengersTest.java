package com.zishi.junit.ch22;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit5.ArquillianExtension;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.spec.JavaArchive;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;

import javax.inject.Inject;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Arquillian 和 Junit5 的集成
 */
@ExtendWith(ArquillianExtension.class)
class FlightWithPassengersTest {

    /**
     * 静态的方法，返回一个JavaArchive（归档）
     * 该归档的作用是隔离测试所需的类和资源文件(resources)
     * 归档是通过ShrinkWrap定义的
     * 这种微部署的策略可以让我们更加精确的关注我们要测试的东西
     * 结果就是：测试非常的精简，并且容易管理
     *
     * @return
     */
    @Deployment
    public static JavaArchive createDeployment() {
        return ShrinkWrap.create(JavaArchive.class)
                .addClasses(Passenger.class, Flight.class, FlightProducer.class)
                .addAsManifestResource(EmptyAsset.INSTANCE, "beans.xml");
    }

    @Inject
    @FlightNumber(number = "AA1234")
    Flight flight;

    @Mock
    DistancesManager distancesManager;

    @Rule
    public MockitoRule mockitoRule = MockitoJUnit.rule();

    private static Map<Passenger, Integer> passengersPointsMap = new HashMap<>();

    @BeforeClass
    public static void setUp() {
        passengersPointsMap.put(new Passenger("900-45-6809", "Susan Todd", "GB"), 210);
        passengersPointsMap.put(new Passenger("900-45-6797", "Harry Christensen", "GB"), 420);
        passengersPointsMap.put(new Passenger("123-45-6799", "Bethany King", "US"), 630);
    }

    @Test
    public void testFlightsDistances() {
        Mockito.when(distancesManager.getPassengersPointsMap()).thenReturn(passengersPointsMap);
        assertEquals(210, distancesManager.getPassengersPointsMap().get(new Passenger("900-45-6809", "Susan Todd", "GB")).longValue());
        assertEquals(420, distancesManager.getPassengersPointsMap().get(new Passenger("900-45-6797", "Harry Christensen", "GB")).longValue());
        assertEquals(630, distancesManager.getPassengersPointsMap().get(new Passenger("123-45-6799", "Bethany King", "US")).longValue());
    }

    // junit5 的 @Test
    @Test
    public void testNumberOfSeatsCannotBeExceeded() throws IOException {
        assertEquals(50, flight.getPassengersNumber());
        flight.addPassenger(new Passenger("124-56-7890", "Michael Johnson", "US"));
    }

    @Test
    public void testAddRemovePassengers() throws IOException {
        flight.setSeats(51);
        Passenger additionalPassenger = new Passenger("124-56-7890", "Michael Johnson", "US");
        flight.addPassenger(additionalPassenger);
        assertEquals(51, flight.getPassengersNumber());
        flight.removePassenger(additionalPassenger);
        assertEquals(50, flight.getPassengersNumber());
        assertEquals(51, flight.getSeats());
    }
}
