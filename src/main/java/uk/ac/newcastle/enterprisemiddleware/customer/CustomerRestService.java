package uk.ac.newcastle.enterprisemiddleware.customer;

import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.media.Schema;
import org.eclipse.microprofile.openapi.annotations.parameters.Parameter;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponses;
import uk.ac.newcastle.enterprisemiddleware.area.InvalidAreaCodeException;
import uk.ac.newcastle.enterprisemiddleware.util.RestServiceException;

import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.NoResultException;
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

@Path("/customer")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class CustomerRestService {
    @Inject
    @Named("logger")
    Logger log;

    @Inject
    CustomerService service;


    @GET
    @Operation(summary = "Fetch all Customers", description = "Returns a JSON array of all stored Customer objects.")
    public Response retrieveAllCustomers() {
        //Create an empty collection to contain the intersection of Contacts to be returned
        List<Customer> customers=service.findAllCustomers();
        return Response.ok(customers).build();
    }


//    @GET
//    @Path("/findAllCustomersByName")
//    @Operation(summary = "Fetch all Customers by Name", description = "Returns a JSON array of all stored Customer objects.")
//    public Response retrieveAllCustomersByName(@QueryParam("name") String name) {
//        //Create an empty collection to contain the intersection of Contacts to be returned
//        List<Customer> customers=null;
//
//        if(name == null) {
//            customers = service.findAllCustomersByName(name);
//        }
//
//        return Response.ok(customers).build();
//    }


    @GET
    @Path("/{id:[0-9]+}")
    @Operation(summary="Fetch all Customers by ID",description = "Returns a JSON array of all stored Customer objects.")
    public Response findAllCustomersById(@Parameter(description = "Id of Customer to be fetched")
                                             @Schema(minimum = "0", required = true)
                                             @PathParam("id")
                                             long id){
        //Create an empty collection to contain the intersection of Contacts to be returned
        Customer customer = service.findAllCustomersById(id);
        if (customer == null) {
            // Verify that the contact exists. Return 404, if not present.
            throw new RestServiceException("No Customer with the id " + id + " was found!", Response.Status.NOT_FOUND);
        }
        log.info("findById " + id + ": found Customer = " + customer);

        return Response.ok(customer).build();
    }

    @GET
    @Path("/email/{email}")
    @Operation(summary="Fetch all Customers by email",description = "Returns a JSON array of all stored Customer objects.")
    public Response findAllCustomersByEmail(@QueryParam("email") String email){
        //Create an empty collection to contain the intersection of Contacts to be returned
        Customer customers;
        try {
            customers= service.findAllCustomersByEmail(email);
        } catch (NoResultException e) {
            // Verify that the contact exists. Return 404, if not present.
            throw new RestServiceException("No Contact with the email " + email + " was found!", Response.Status.NOT_FOUND);
        }
        return Response.ok(customers).build();
    }

    @POST
    @Operation(description = "Add a new Customer to the database")
    @APIResponses(value = {
            @APIResponse(responseCode = "201", description = "Customer created successfully."),
            @APIResponse(responseCode = "400", description = "Invalid Customer supplied in request body"),
            @APIResponse(responseCode = "409", description = "Customer supplied in request body conflicts with an existing Customer"),
            @APIResponse(responseCode = "500", description = "An unexpected error occurred whilst processing the request")
    })
    @Transactional
    public Response createCustomer(
            @Parameter(description = "JSON representation of Customer object to be added to the database", required = true)
            Customer Customer) {

        if (Customer == null) {
            throw new RestServiceException("Bad Request", Response.Status.BAD_REQUEST);
        }

        Response.ResponseBuilder builder;

        try {
            // Clear the ID if accidentally set
            Customer.setId(null);
            // Go add the new Customer.
            service.create(Customer);
            // Create a "Resource Created" 201 Response and pass the Customer back in case it is needed.
            builder = Response.status(Response.Status.CREATED).entity(Customer);

        }
        catch (ConstraintViolationException ce) {
            //Handle bean validation issues
            Map<String, String> responseObj = new HashMap<>();

            for (ConstraintViolation<?> violation : ce.getConstraintViolations()) {
                responseObj.put(violation.getPropertyPath().toString(), violation.getMessage());
            }
            throw new RestServiceException("Bad Request", responseObj, Response.Status.BAD_REQUEST, ce);

        } catch (UniqueEmailException e) {
            // Handle the unique constraint violation
            Map<String, String> responseObj = new HashMap<>();
            responseObj.put("email", "That email is already used, please use a unique email");
            throw new RestServiceException("Bad Request", responseObj, Response.Status.CONFLICT, e);
        }
        catch (Exception e) {
            // Handle generic exceptions
            throw new RestServiceException(e);
        }


        log.info("createCustomer completed. Customer = " + Customer);
        return builder.build();
    }


    @DELETE
    @Path("/{id:[0-9]+}")
    @Operation(description = "Delete a Contact from the database")
    @APIResponses(value = {
            @APIResponse(responseCode = "204", description = "The customer has been successfully deleted"),
            @APIResponse(responseCode = "400", description = "Invalid Customer id supplied"),
            @APIResponse(responseCode = "404", description = "Customer with id not found"),
            @APIResponse(responseCode = "500", description = "An unexpected error occurred whilst processing the request")
    })
    @Transactional
    public Response deleteContact(
            @Parameter(description = "Id of Customer to be deleted", required = true)
            @Schema(minimum = "0")
            @PathParam("id")
            long id) {

        Response.ResponseBuilder builder;

        Customer customer = service.findAllCustomersById(id);
        if (customer == null) {
            // Verify that the customer exists. Return 404, if not present.
            throw new RestServiceException("No Customer with the id " + id + " was found!", Response.Status.NOT_FOUND);
        }

        try {
            service.delete(customer);

            builder = Response.noContent();

        } catch (Exception e) {
            // Handle generic exceptions
            throw new RestServiceException(e);
        }
        log.info("deleteCustomer completed. Customer = " + customer);
        return builder.build();
    }











}

