package uk.ac.newcastle.enterprisemiddleware.booking;

import uk.ac.newcastle.enterprisemiddleware.customer.Customer;
import uk.ac.newcastle.enterprisemiddleware.flight.Flight;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.NoResultException;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.validation.ValidationException;
import javax.validation.Validator;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;
@ApplicationScoped
public class BookingValidator {
    @Inject
    Validator validator;

    @Inject
    BookingRepository crud;


    void validateBooking(Booking booking) throws ConstraintViolationException, ValidationException {
        // Create a bean validator and check for issues.
        Set<ConstraintViolation<Booking>> violations = validator.validate(booking);

        if (!violations.isEmpty()) {
            throw new ConstraintViolationException(new HashSet<ConstraintViolation<?>>(violations));
        }
//        if (BookingExists(booking.getBookingDate(), booking.getFlight().getId(), booking.getCustomer().getId())){
//            throw new UniqueBookingException("Unique Booking Violation");
//        }

    }

//   boolean BookingExists(Date date, Long flightId,Long customerId){
//        Booking booking = null;
//        Booking bookingWithID = null;
//        try {
//            booking = crud.findByDateAndFlightIdAndCustomerId(booking.getFlight().getId(),booking.getBookingDate(), booking.getCustomer().getId());
//        } catch (NoResultException e) {
//            // ignore
//        }
//
//        if (flightId != null && date != null && customerId !=null) {
//            try {
//                bookingWithID = crud.findByDateAndFlightIdAndCustomerId(flightId, date,customerId);
//                if (bookingWithID != null && bookingWithID.getFlightId().equals(flightId) && bookingWithID.getBookingDate().equals(date) && bookingWithID.getCustomerId().equals(customerId)) {
//                    booking = null;
//                }
//            } catch (NoResultException e) {
//                // ignore
//            }
//        }
//
//        return booking != null;
//    }


}
