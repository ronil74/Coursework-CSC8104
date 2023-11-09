package uk.ac.newcastle.enterprisemiddleware.taxi;

import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;
import uk.ac.newcastle.enterprisemiddleware.hotel.Hotel;
import uk.ac.newcastle.enterprisemiddleware.hotel.HotelBooking;

import javax.ws.rs.*;
import java.util.List;

@Path("/taxi")
@RegisterRestClient(configKey = "taxi-api")
public interface TaxiService {


    @GET
    List<Taxi> taxi();

    @GET
    @Path("/{id:[0-9]+}")
    Taxi getTaxiById(@PathParam("id") long id);

    @POST
    Taxi create(Taxi taxi);

    @DELETE
    @Path("/{id:[0-9]+}")
    Hotel delete(@PathParam("id") long id);



}
