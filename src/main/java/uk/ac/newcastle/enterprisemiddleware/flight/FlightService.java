package uk.ac.newcastle.enterprisemiddleware.flight;

import org.eclipse.microprofile.rest.client.inject.RestClient;
import uk.ac.newcastle.enterprisemiddleware.area.Area;
import uk.ac.newcastle.enterprisemiddleware.area.AreaService;
import uk.ac.newcastle.enterprisemiddleware.area.InvalidAreaCodeException;

import javax.enterprise.context.Dependent;
import javax.inject.Inject;
import javax.inject.Named;
import javax.validation.ConstraintViolationException;
import javax.ws.rs.ClientErrorException;
import javax.ws.rs.core.Response;
import java.util.List;
import java.util.logging.Logger;
@Dependent
public class FlightService {

    @Inject
    @Named("logger")
    Logger log;

//    @Inject
//    FlightValidator validator;

    @Inject
    FlightRepository crud;

    public List<Flight> findAllFlights() {
        return crud.findAllFlights();
    }

    public Flight findFlightById(long flight){
        return crud.findById(flight);
    }

    public Flight findByFlightNumber(String flightNumber) {
        return crud.findByFlightNumber(flightNumber);
    }

    public Flight create(Flight flight) {
        return crud.create(flight);

    }
    public Flight delete(Flight flight) {
        log.info("delete() - Deleting " + flight.toString());

        Flight deletedFlight = null;

        if (flight.getId() != null) {
            deletedFlight = crud.delete(flight);
        } else {
            log.info("delete() - No ID was found so can't Delete.");
        }

        return deletedFlight;
    }


}
