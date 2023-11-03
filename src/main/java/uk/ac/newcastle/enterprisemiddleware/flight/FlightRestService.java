package uk.ac.newcastle.enterprisemiddleware.flight;

import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.media.Schema;
import org.eclipse.microprofile.openapi.annotations.parameters.Parameter;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponses;
import uk.ac.newcastle.enterprisemiddleware.area.InvalidAreaCodeException;
import uk.ac.newcastle.enterprisemiddleware.util.RestServiceException;

import javax.inject.Inject;
import javax.inject.Named;
import javax.transaction.Transactional;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

@Path("/flight")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class FlightRestService {
    @Inject
    @Named("logger")
    Logger log;

    @Inject
    FlightService service;

    @GET
    @Path("/findAllFlights")
    @Operation(summary = "Fetch all flights", description = "Returns a JSON array of all stored flight objects.")
    public Response findAllFlights() {
        List<Flight> flights;
        flights = service.findAllFlights();
        return Response.ok(flights).build();
    }



    @GET
    @Path("/findFlightById/{id:[0-9]+}")
    @Operation(
            summary = "Fetch a flight by id",
            description = "Returns a JSON representation of the flight object with the provided id."
    )
    @APIResponses(value = {
            @APIResponse(responseCode = "200", description = "Flight found"),
            @APIResponse(responseCode = "404", description = "Flight with id not found")
    })
    public Response findFlightById(@Parameter(description = "Id of Flight to be fetched")
                                       @Schema(minimum = "0", required = true)
                                       @PathParam("id")
                                       long id){
        //Create an empty collection to contain the intersection of Flight to be returned
        Flight flight = service.findFlightById(id);
        if (flight == null) {
            // Verify that the flight exists. Return 404, if not present.
            throw new RestServiceException("No Flight with the id " + id + " was found!", Response.Status.NOT_FOUND);
        }
        log.info("findById " + id + ": found flight = " + flight);

        return Response.ok(flight).build();
    }


    @POST
    @Path("/createFlight")
    @Operation(
            summary = "post a new flight",
            description = "posts a new flight object to the database.")
    @APIResponses(value = {
            @APIResponse(responseCode = "201", description = "Flight created successfully."),
            @APIResponse(responseCode = "400", description = "Invalid flight object supplied in request body"),
            @APIResponse(responseCode = "409", description = "flight object already exist in databases"),
            @APIResponse(responseCode = "500", description = "An unexpected error occurred whilst processing the request")
    })
    @Transactional
    public Response createFlight(@Parameter(description = "JSON representation of Flight object to be added to the database", required = true)
                                     Flight flight){
        if (flight == null) {
            throw new RestServiceException("Bad Request", Response.Status.BAD_REQUEST);
        }
//        if (service.findByFlightNumber(flight.getFlightNumber()) != null) {
//            throw new RestServiceException("Flight Conflict", Response.Status.CONFLICT);
//        }
        Response.ResponseBuilder builder;
        try {
            flight.setId(null);
            service.create(flight);
            builder = Response.status(Response.Status.CREATED).entity(flight);
        }  catch (UniqueFlightNumberException e) {
            // Handle the unique constraint violation
            Map<String, String> responseObj = new HashMap<>();
            responseObj.put("flightNumber", "That Flight Number is already in use, please use a unique Flight Number");
            throw new RestServiceException("Bad Request", responseObj, Response.Status.CONFLICT, e);
        } catch(UniqueDepartureAndDestinationException e){
            Map<String, String> responseObj = new HashMap<>();
            responseObj.put("Destination", "Error check whether departure and destination are not same");
            throw new RestServiceException("Bad Request", responseObj, Response.Status.CONFLICT, e);
        }catch (ConstraintViolationException ce) {
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

        log.info("createFlight completed. Flight = " + flight);
        return builder.build();

    }

    @DELETE
    @Path("/deleteFlight/{id:[0-9]+}")
    @Operation(description = "Delete a Flight object from the database")
    @APIResponses(value = {
            @APIResponse(responseCode = "204", description = "The Flight has been successfully deleted"),
            @APIResponse(responseCode = "400", description = "Invalid flight id supplied"),
            @APIResponse(responseCode = "404", description = "flight with id not found"),
            @APIResponse(responseCode = "500", description = "An unexpected error occurred whilst processing the request")
    })
    @Transactional
    public Response deleteFlight(
            @Parameter(description = "Id of Flight to be deleted", required = true)
            @Schema(minimum = "0")
            @PathParam("id")
            long id) {

        Response.ResponseBuilder builder;

        Flight flight = service.findFlightById(id);
        if (flight == null) {
            // Verify that the flight exists. Return 404, if not present.
            throw new RestServiceException("No Flight with the id " + id + " was found!", Response.Status.NOT_FOUND);
        }

        try {
            service.delete(flight);

            builder = Response.noContent();

        }  catch (UniqueFlightNumberException e) {
            // Handle the unique constraint violation
            Map<String, String> responseObj = new HashMap<>();
            responseObj.put("flightNumber", "That Flight Number is already in use, please use a unique Flight Number");
            throw new RestServiceException("Bad Request", responseObj, Response.Status.CONFLICT, e);
        } catch(UniqueDepartureAndDestinationException e){
            Map<String, String> responseObj = new HashMap<>();
            responseObj.put("Destination", "Error check whether departure and destination are not same");
            throw new RestServiceException("Bad Request", responseObj, Response.Status.CONFLICT, e);
        }catch (ConstraintViolationException ce) {
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
        log.info("deleteFlight completed. Flight = " + flight);
        return builder.build();
    }




}
