package uk.ac.newcastle.enterprisemiddleware.booking;

import org.eclipse.microprofile.rest.client.inject.RestClient;
import uk.ac.newcastle.enterprisemiddleware.area.InvalidAreaCodeException;
import uk.ac.newcastle.enterprisemiddleware.flight.Flight;

import javax.enterprise.context.Dependent;
import javax.inject.Inject;
import javax.inject.Named;
import javax.validation.ConstraintViolationException;
import javax.ws.rs.ClientErrorException;
import javax.ws.rs.core.Response;
import java.util.Date;
import java.util.List;
import java.util.logging.Logger;

@Dependent
public class BookingService {
    @Inject
    @Named("logger")
    Logger log;

//    @Inject
//    ContactValidator validator;

    @Inject
    BookingRepository crud;

    List<Booking> findAll() {
        return crud.findAll();
    }
    Booking findById(Long id) {
        return crud.findById(id);
    }

//     public Booking findByDateAndFlightId(Long flightId, Date date) {
//        return BookingRepository.findByDateAndFlightId(flightId, date);
//    }

    public Booking create(Booking booking) throws Exception {
        return crud.create(booking);
    }

    public Booking delete(Booking booking) throws Exception {
        log.info("delete() - Deleting " + booking.toString());

        Booking deletedBooking = null;

        if (booking.getId() != null) {
            deletedBooking = crud.delete(booking);
        } else {
            log.info("delete() - No ID was found so can't Delete.");
        }

        return deletedBooking;
    }

}
