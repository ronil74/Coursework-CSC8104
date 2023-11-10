package uk.ac.newcastle.enterprisemiddleware.travelAgent;

import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.media.Schema;
import org.eclipse.microprofile.openapi.annotations.parameters.Parameter;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponses;
import org.eclipse.microprofile.rest.client.inject.RestClient;
import uk.ac.newcastle.enterprisemiddleware.area.InvalidAreaCodeException;
import uk.ac.newcastle.enterprisemiddleware.booking.Booking;
import uk.ac.newcastle.enterprisemiddleware.booking.BookingService;
import uk.ac.newcastle.enterprisemiddleware.customer.Customer;
import uk.ac.newcastle.enterprisemiddleware.customer.CustomerService;
import uk.ac.newcastle.enterprisemiddleware.flight.Flight;
import uk.ac.newcastle.enterprisemiddleware.flight.FlightService;
import uk.ac.newcastle.enterprisemiddleware.hotel.HotelBooking;
import uk.ac.newcastle.enterprisemiddleware.hotel.HotelBookingService;
import uk.ac.newcastle.enterprisemiddleware.taxi.TaxiBooking;
import uk.ac.newcastle.enterprisemiddleware.taxi.TaxiBookingService;
import uk.ac.newcastle.enterprisemiddleware.util.RestServiceException;

import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.NoResultException;
import javax.transaction.SystemException;
import javax.transaction.Transactional;
import javax.transaction.UserTransaction;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;
@Path("/travelAgent")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class TravelAgentRestService {
    @Inject
    @Named("logger")
    Logger log;

    @Inject
    CustomerService customerService;

    @Inject
    BookingService bookingService;

    @RestClient
    HotelBookingService hotelBookingService;
    @RestClient
    TaxiBookingService taxiBookingService;

    @Inject
    TravelAgentService travelAgentService;
    @Inject
    private UserTransaction userTransaction;

    @GET
    @Operation(summary = "Fetch all TravelAgent", description = "Returns a JSON array of all stored TravelAgent objects.")
    public Response retrieveAll() {
        List<TravelAgentBooking> travelAgentBookings = travelAgentService.findAll();
        return Response.ok(travelAgentBookings).build();
    }

    @GET
    @Path("/{customerId:[0-9]+}")
    @Operation(summary = "Fetch all TravelAgent", description = "Returns a JSON array of all stored TravelAgent objects.")
    public Response retrieveAllBookingsByCId(
            @Parameter(description = "Id of Customer to be fetched")
            @Schema(minimum = "0", required = true)
            @PathParam("customerId")
            long customerId) {
        Customer customer = customerService.findAllCustomersById(customerId);
        if (customer == null) {
            throw new RestServiceException("No Customer with the customerId " + customerId + " was found!", Response.Status.NOT_FOUND);
        }
        List<TravelAgentBooking> travelAgentBookings = travelAgentService.findByCustomer(customerId);
        return Response.ok(travelAgentBookings).build();
    }

    @POST
    @Operation(description = "Add a new TravelAgent to the database")
    @APIResponses(value = {
            @APIResponse(responseCode = "201", description = "Travel Agent Booking created successfully."),
            @APIResponse(responseCode = "400", description = "Invalid Agent supplied in request body"),
            @APIResponse(responseCode = "409", description = "Agent supplied in request body conflicts with an existing Contact"),
            @APIResponse(responseCode = "500", description = "An unexpected error occurred whilst processing the request")
    })
    @Transactional
    public Response createTravelAgent(
            @Parameter(description =
                    "JSON representation of TravelAgent object to be added to the database", required = true)
            TravelAgent travelAgent) throws SystemException {

        if (travelAgent == null) {
            throw new RestServiceException("Bad Request", Response.Status.BAD_REQUEST);
        }
        if (travelAgent.getCustomer() == null) {
            throw new RestServiceException("TravelAgent doesn't exsit", Response.Status.BAD_REQUEST);
        }
        Response.ResponseBuilder builder;

        try {

            Customer customer =travelAgent.getCustomer() ;
            System.out.println(customer.getEmail());
            Customer newCustomer = customerService.findAllCustomersById(customer.getId());
            System.out.println(customer.getEmail());
            if (newCustomer == null) {
                customer.setId(null);
                newCustomer = customerService.create(customer);
            }

            TravelAgentBooking travelAgentBooking = new TravelAgentBooking();

            Booking booking=travelAgent.getFlight();
            booking.setId(null);
            booking.setCustomerId(customer.getId());
            bookingService.create(booking);

//            TaxiBooking taxiBooking=taxiBookingService.createTaxiBooking(travelAgent.getTaxiBooking());
            HotelBooking hotelBooking;
            System.out.println(hotelBookingService.getHotelBooking());
          hotelBooking=hotelBookingService.createHotelBooking(travelAgent.getHotelBooking());


            travelAgentBooking.setId(null);
            travelAgentBooking.setCustomerId(travelAgent.getCustomer().getId());
            System.out.println(travelAgent.getCustomer().getId());
            travelAgentBooking.setFlightId(booking.getFlightId());
            System.out.println(travelAgent.getFlight().getFlightId());
            travelAgentBooking.setHotelId(hotelBooking.getHotelId());
//            travelAgentBooking.setTaxiId(taxiBooking.getTaxiId());
//            travelAgentBooking.setTaxiId(1L);

            travelAgentBooking = travelAgentService.create(travelAgentBooking);
            builder = Response.status(Response.Status.CREATED).entity(travelAgentBooking);
//            userTransaction.commit();
        } catch (Exception e) {
//            userTransaction.rollback();
            e.printStackTrace();
            System.out.println(e);
            throw new RestServiceException(e);
        }
        return builder.build();
    }


    @DELETE
    @Operation(description = "Delete TravelAgent to the database")
    @APIResponses(value = {
            @APIResponse(responseCode = "201", description = "TravelAgent delete successfully.")
    })
    @Transactional
    public Response deleteTravelAgent(
            @Parameter
            Long id) {
        TravelAgentBooking travelAgentBooking = travelAgentService.findById(id);
        Customer customer = customerService.findAllCustomersById(travelAgentBooking.getCustomerId());
        if (customer == null) {
            throw new RestServiceException("TravelAgent not exsit", Response.Status.BAD_REQUEST);
        }
        Response.ResponseBuilder builder;

        try {
//            taxiBookingService.deleteTaxiBooking(travelAgentBooking.getTaxiId());
            hotelBookingService.deleteHotelBooking(travelAgentBooking.getHotelId());
            Booking booking=bookingService.findById(travelAgentBooking.getFlightId());
            bookingService.delete(booking);

            travelAgentService.delete(travelAgentBooking);


            builder = Response.ok(travelAgentBooking);

        } catch (ConstraintViolationException ce) {
            //Handle bean validation issues
            Map<String, String> responseObj = new HashMap<>();

            for (ConstraintViolation<?> violation : ce.getConstraintViolations()) {
                responseObj.put(violation.getPropertyPath().toString(), violation.getMessage());
            }
            throw new RestServiceException("Bad Request", responseObj, Response.Status.BAD_REQUEST, ce);

        } catch (Exception e) {
            throw new RestServiceException(e);
        }
        return builder.build();
    }
}
