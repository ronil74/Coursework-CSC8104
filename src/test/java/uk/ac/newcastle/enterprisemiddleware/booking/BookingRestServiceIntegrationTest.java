package uk.ac.newcastle.enterprisemiddleware.booking;

import io.quarkus.test.common.QuarkusTestResource;
import io.quarkus.test.common.http.TestHTTPEndpoint;
import io.quarkus.test.h2.H2DatabaseTestResource;
import io.quarkus.test.junit.QuarkusTest;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.junit.jupiter.api.*;
import java.util.Calendar;
import java.util.logging.Logger;

import static io.restassured.RestAssured.given;
import static io.restassured.RestAssured.when;
import static org.hamcrest.CoreMatchers.containsString;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import uk.ac.newcastle.enterprisemiddleware.booking.BookingRestService;
import uk.ac.newcastle.enterprisemiddleware.contact.ContactService;
import uk.ac.newcastle.enterprisemiddleware.customer.Customer;
import uk.ac.newcastle.enterprisemiddleware.customer.CustomerService;
import uk.ac.newcastle.enterprisemiddleware.flight.Flight;
import uk.ac.newcastle.enterprisemiddleware.flight.FlightService;

import javax.inject.Inject;
import javax.inject.Named;

@QuarkusTest
@TestHTTPEndpoint(BookingRestService.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@QuarkusTestResource(H2DatabaseTestResource.class)

 class BookingRestServiceIntegrationTest {
    private static Booking booking;
    private static Flight flight;
    private  static Customer customer;

    @Inject
    @Named("logger")
    Logger log;
    @Inject
    CustomerService customerService;

    @Inject
    FlightService flightService;

    @BeforeAll
    static void setup() {


        flight = new Flight();
        flight.setFlightNumber("M2345");
        flight.setDeparture("MUM");
        flight.setDestination("NCL");

        customer=new Customer();
        customer.setName("Testron");
        customer.setEmail("test@gmail.com");
        customer.setPhoneNumber("09890448159");


        booking = new Booking();
        booking.setBookingDate(Calendar.getInstance().getTime());
        booking.setFlightId(1L);
        booking.setCustomerId(1L);


    }



    @Test
    @Order(1)
    public void testCanCreateBooking() {
        given().
                contentType(ContentType.JSON).
                body(booking).
                when()
                .post("/createBooking").
                then().
                statusCode(201);
    }

    @Test
    @Order(2)
    public void testCanGetBooking() {
        Response response = when().
                get("/findAllBooking").
                then().
                statusCode(200).
                extract().response();

        Booking[] result = response.body().as(Booking[].class);

        System.out.println(result[0]);

        assertEquals(1, result.length);
        assertTrue(booking.getBookingDate().equals(result[0].getBookingDate()), "Booking date check");
        assertTrue(booking.getFlightId().equals(result[0].getFlightId()), "Flight ID check");

    }

    @Test
    @Order(4)
    public void testCanDeleteBooking() {
        Response response = when().
                get().
                then().
                statusCode(200).
                extract().response();

        Booking[] result = response.body().as(Booking[].class);

        when().
                delete("/deleteBooking/{id:[0-9]+}",result[0].getId().toString()).
                then().
                statusCode(204);
    }


}
