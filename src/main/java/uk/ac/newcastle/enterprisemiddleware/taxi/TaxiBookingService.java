package uk.ac.newcastle.enterprisemiddleware.taxi;

import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;
import javax.ws.rs.*;
import java.util.List;

@Path("/bookings")
@RegisterRestClient(configKey = "taxi-api")
public interface TaxiBookingService {
    @GET
    List<TaxiBooking> getTaxiBooking();

    @GET
    @Path("/{id:[0-9]+}")
    TaxiBooking getTaxiById(@PathParam("id") long id);

    @POST
    TaxiBooking createTaxiBooking(TaxiBooking taxiBooking);

    @DELETE
    @Path("/{id:[0-9]+}")
    TaxiBooking deleteTaxiBooking(@PathParam("id") long id);

}
