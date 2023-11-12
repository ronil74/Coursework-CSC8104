package uk.ac.newcastle.enterprisemiddleware.hotel2;

import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.rest.client.inject.RestClient;
import uk.ac.newcastle.enterprisemiddleware.hotel.Hotel;
import uk.ac.newcastle.enterprisemiddleware.hotel.HotelService;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

@Path("/fhotels")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)

public class HotelRestService2 {

    @RestClient
    HotelService2 hotelService2;

    @GET
    @Path("/hotels")
    @Operation(summary = "Fetch all hotels", description = "Returns a JSON array of all stored flight objects.")
    public Response findAllHotels2() {
        List<Hotel2> hotel2;
        hotel2 = hotelService2.hotel2();
        System.out.println("Hello");

        return Response.ok(hotel2).build();
    }
}
