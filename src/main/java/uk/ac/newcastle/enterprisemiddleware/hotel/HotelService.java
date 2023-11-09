package uk.ac.newcastle.enterprisemiddleware.hotel;

import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

import javax.ws.rs.*;
import java.util.List;

@Path("/hotels")
@RegisterRestClient(configKey = "hotel-api")
public interface HotelService {


        @GET
        List<Hotel> hotel();

        @GET
        @Path("/{id:[0-9]+}")
        Hotel getHotelById(@PathParam("id") long id);

        @POST
        Hotel createHotel(Hotel hotel);

        @DELETE
        @Path("/{id:[0-9]+}")
        Hotel deleteHotel(@PathParam("id") long id);



}
