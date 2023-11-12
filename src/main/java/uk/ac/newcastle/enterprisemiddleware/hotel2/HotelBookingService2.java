package uk.ac.newcastle.enterprisemiddleware.hotel2;

import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;
import uk.ac.newcastle.enterprisemiddleware.hotel.HotelBooking;

import javax.ws.rs.*;
import java.util.List;

@Path("/bookings")
@RegisterRestClient(configKey = "hotel2-api")
public interface HotelBookingService2 {

    @GET
    List<HotelBooking2> getHotelBooking2();

    @GET
    @Path("/{id:[0-9]+}")
    HotelBooking2 getHotelById2(@PathParam("id") int id);

    @POST
    HotelBooking2 createHotelBooking2(HotelBooking2 hotelBooking);

    @DELETE
    @Path("/{id:[0-9]+}")
    HotelBooking2 deleteHotelBooking2(@PathParam("id") int id);

}