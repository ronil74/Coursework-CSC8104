package uk.ac.newcastle.enterprisemiddleware.guestBooking;
import io.quarkus.test.common.QuarkusTestResource;
import io.quarkus.test.common.http.TestHTTPEndpoint;
import io.quarkus.test.h2.H2DatabaseTestResource;
import io.quarkus.test.junit.QuarkusTest;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.junit.jupiter.api.*;
import uk.ac.newcastle.enterprisemiddleware.booking.Booking;
import uk.ac.newcastle.enterprisemiddleware.contact.ContactRestService;
import uk.ac.newcastle.enterprisemiddleware.flight.Flight;
import uk.ac.newcastle.enterprisemiddleware.flight.FlightRestService;
import uk.ac.newcastle.enterprisemiddleware.guestbooking.GuestBooking;
import uk.ac.newcastle.enterprisemiddleware.guestbooking.GuestBookingRestService;
import uk.ac.newcastle.enterprisemiddleware.customer.*;
import uk.ac.newcastle.enterprisemiddleware.booking.*;

import java.util.Calendar;

import static io.restassured.RestAssured.given;
import static io.restassured.RestAssured.when;
import static org.hamcrest.CoreMatchers.containsString;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@QuarkusTest
@TestHTTPEndpoint(GuestBookingRestService.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@QuarkusTestResource(H2DatabaseTestResource.class)

public class GuestBookingServiceIntegrationTest {
    private static GuestBooking guestBooking;
    private static Booking booking;
    private static Customer customer;
    private static Flight flight;

    @BeforeAll
    static void setup() {
        guestBooking = new GuestBooking();

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

        guestBooking.setCustomer(customer);
        guestBooking.setBooking(booking);


    }


    @Test
    @Order(1)
    public void testCanCreateGuestBooking() {
        given().
                contentType(ContentType.JSON).
                body(guestBooking).
                when()
                .post().
                then().
                statusCode(201);
    }
}
