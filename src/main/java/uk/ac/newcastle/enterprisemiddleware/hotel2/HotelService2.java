package uk.ac.newcastle.enterprisemiddleware.hotel2;

import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;
import uk.ac.newcastle.enterprisemiddleware.hotel.Hotel;

import javax.ws.rs.*;
import java.util.List;

@Path("/hotels")
@RegisterRestClient(configKey = "hotel2-api")
public interface HotelService2 {


    @GET
    List<Hotel2> hotel2();

    @GET
    @Path("/{id:[0-9]+}")
    Hotel2 getHotel2ById(@PathParam("id") long id);

    @POST
    Hotel2 createHotel2(Hotel2 hotel);

    @DELETE
    @Path("/{id:[0-9]+}")
    Hotel2 deleteHotel2(@PathParam("id") long id);



}
