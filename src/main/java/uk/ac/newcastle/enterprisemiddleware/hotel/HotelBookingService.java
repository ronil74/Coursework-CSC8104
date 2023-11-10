package uk.ac.newcastle.enterprisemiddleware.hotel;

import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;


import javax.ws.rs.*;
import java.util.List;

@Path("/bookings")
@RegisterRestClient(configKey = "hotel-api")
public interface HotelBookingService {

    @GET
    @Path("/all")
    List<HotelBooking> getHotelBooking();

    @GET
    @Path("/{id:[0-9]+}")
    HotelBooking getHotelById(@PathParam("id") int id);

    @POST
    HotelBooking createHotelBooking(HotelBooking hotelBooking);

    @DELETE
    @Path("/{id:[0-9]+}")
    HotelBooking deleteHotelBooking(@PathParam("id") int id);

}
