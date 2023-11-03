package uk.ac.newcastle.enterprisemiddleware.taxi;


import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;


import javax.ws.rs.*;
import java.util.List;

@Path("/taxi")
@RegisterRestClient(configKey = "taxi-api")
public interface TaxiService {
    @GET
    List<Taxi> getTaxi();

    @GET
    @Path("/{id:[0-9]+}")
    Taxi getTaxiById(@PathParam("id") int id);

    @POST
    Taxi createTaxiBooking(Taxi taxi);

    @DELETE
    @Path("/{id:[0-9]+}")
    Taxi deleteTaxiBooking(@PathParam("id") int id);

}
