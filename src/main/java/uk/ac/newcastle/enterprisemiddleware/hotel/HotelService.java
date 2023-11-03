package uk.ac.newcastle.enterprisemiddleware.hotel;

import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;
import uk.ac.newcastle.enterprisemiddleware.taxi.Taxi;

import javax.ws.rs.*;
import java.util.List;

@Path("/hotel")
@RegisterRestClient(configKey = "hotel-api")
public interface HotelService {

    @GET
    List<Hotel> getHotel();

    @GET
    @Path("/{id:[0-9]+}")
    Hotel getHotelById(@PathParam("id") int id);

    @POST
    Hotel createHotelBooking(Hotel hotel);

    @DELETE
    @Path("/{id:[0-9]+}")
    Hotel deleteHotelBooking(@PathParam("id") int id);

}
