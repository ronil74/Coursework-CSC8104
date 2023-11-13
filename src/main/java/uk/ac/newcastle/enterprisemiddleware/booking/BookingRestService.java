package uk.ac.newcastle.enterprisemiddleware.booking;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.media.Schema;
import org.eclipse.microprofile.openapi.annotations.parameters.Parameter;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponses;
import org.jboss.resteasy.reactive.Cache;
import uk.ac.newcastle.enterprisemiddleware.area.InvalidAreaCodeException;
import uk.ac.newcastle.enterprisemiddleware.contact.UniqueEmailException;
import uk.ac.newcastle.enterprisemiddleware.customer.*;
import uk.ac.newcastle.enterprisemiddleware.customer.CustomerService;
import uk.ac.newcastle.enterprisemiddleware.flight.*;
import uk.ac.newcastle.enterprisemiddleware.flight.FlightService;
import uk.ac.newcastle.enterprisemiddleware.util.RestServiceException;

import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.NoResultException;
import javax.transaction.Transactional;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.validation.Valid;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

@Path("/flightbooking")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class BookingRestService {
    @Inject
    @Named("logger")
    Logger log;

    @Inject
    BookingService service;
    @Inject
    CustomerService customerService;

    @Inject
    FlightService flightService;


    @GET
    @Path("/findAllBooking")
    @Operation(summary = "Fetch all Booking", description = "Returns a JSON array of all stored Booking objects.")
    public Response findAllBookings() {
        List<Booking> booking;
        booking = service.findAll();
        return Response.ok(booking).build();
    }

    @GET
    @Path("/findFlightByBookingId/{id:[0-9]+}")
    @Operation(
            summary = "Fetch a Booking by id",
            description = "Returns a JSON representation of the flight object with the provided id."
    )
    @APIResponses(value = {
            @APIResponse(responseCode = "200", description = "Booking found"),
            @APIResponse(responseCode = "404", description = "Booking with id not found")
    })
    public Response findById(@Parameter(description = "Id of Booking to be fetched")
                             @Schema(minimum = "0", required = true)
                             @PathParam("id")
                             long id) {
        //Create an empty collection to contain the intersection of Contacts to be returned
        Booking booking = service.findById(id);
        if (booking == null) {
            // Verify that the contact exists. Return 404, if not present.
            throw new RestServiceException("No Booking with the id " + id + " was found!", Response.Status.NOT_FOUND);
        }
        log.info("findById " + id + ": found Booking = " + booking);

        return Response.ok(booking).build();
    }


    @POST
    @Path("/createBooking")
    @Operation(
            summary = "post a new booking",
            description = "posts a new booking object to the database.")
    @APIResponses(value = {
            @APIResponse(responseCode = "201", description = "Booking created successfully."),
            @APIResponse(responseCode = "400", description = "Invalid booking object supplied in request body"),
            @APIResponse(responseCode = "409", description = "booking object already exist in databases"),
            @APIResponse(responseCode = "500", description = "An unexpected error occurred whilst processing the request")
    })
    @Transactional
    public Response createFlightBooking(@Valid Booking Booking) {
        if (Booking == null) {
            throw new RestServiceException("Bad Request", Response.Status.BAD_REQUEST);
        }
        Flight flight = flightService.findFlightById(Booking.getFlightId());
        Customer customer = customerService.findAllCustomersById(Booking.getCustomerId());

        if (flight == null) {
            throw new RestServiceException("No flight id " + Booking.getFlightId() +
                    " was found", Response.Status.NOT_FOUND);
        } else if (customer == null) {
            throw new RestServiceException("No customer id " + Booking.getCustomerId() +
                    " was found", Response.Status.NOT_FOUND);
        }

        Booking.setId(null);
        Booking.setFlight(flight);
        Booking.setCustomer(customer);
        try {
            service.create(Booking);
        }
        catch (ConstraintViolationException ce) {
            //Handle bean validation issues
            Map<String, String> responseObj = new HashMap<>();

            for (ConstraintViolation<?> violation : ce.getConstraintViolations()) {
                responseObj.put(violation.getPropertyPath().toString(), violation.getMessage());
            }
            throw new RestServiceException("Bad Request", responseObj, Response.Status.BAD_REQUEST, ce);

        } catch (Exception e) {
            log.severe(" create booking " +
                    "[" + Booking + "] error");
            e.printStackTrace();
            throw new RestServiceException(e.getMessage(), e);
        }
        return Response.ok(Booking).status(Response.Status.CREATED).build();
    }



    @DELETE
    @Path("/deleteBooking/{id:[0-9]+}")
    @Operation(description = "Delete a Booking object from the database")
    @APIResponses(value = {
            @APIResponse(responseCode = "204", description = "The Booking has been successfully deleted"),
            @APIResponse(responseCode = "400", description = "Invalid booking id supplied"),
            @APIResponse(responseCode = "404", description = "flight with id not found"),
            @APIResponse(responseCode = "500", description = "An unexpected error occurred whilst processing the request")
    })
    @Transactional
    public Response deleteBooking(
            @Parameter(description = "Booking ID to be deleted", required = true)
            @Schema(minimum = "0")
            @PathParam("id")
            long id) {

        Response.ResponseBuilder builder;

        Booking booking = service.findById(id);
        if (booking == null) {
            // Verify that the booking exists. Return 404, if not present.
            throw new RestServiceException("No Booking with the id " + id + " was found!", Response.Status.NOT_FOUND);
        }

        try {
            service.delete(booking);

            builder = Response.noContent();

        } catch (ConstraintViolationException ce) {
            //Handle bean validation issues
            Map<String, String> responseObj = new HashMap<>();

            for (ConstraintViolation<?> violation : ce.getConstraintViolations()) {
                responseObj.put(violation.getPropertyPath().toString(), violation.getMessage());
            }
            throw new RestServiceException("Bad Request", responseObj, Response.Status.BAD_REQUEST, ce);

        }catch (Exception e) {
            // Handle generic exceptions
            throw new RestServiceException(e);
        }
        log.info("deleteBooking completed. Booking = " + booking);
        return builder.build();
    }


}
