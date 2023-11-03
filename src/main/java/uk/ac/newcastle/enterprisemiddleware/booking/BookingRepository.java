package uk.ac.newcastle.enterprisemiddleware.booking;

import uk.ac.newcastle.enterprisemiddleware.customer.Customer;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.util.Date;
import java.util.List;
import java.util.logging.Logger;

@RequestScoped
public class BookingRepository {

    @Inject
    @Named("logger")
    Logger log;

    @Inject
    EntityManager em;

    List<Booking> findAll() {
        TypedQuery<Booking> query = em.createNamedQuery(Booking.FIND_ALL, Booking.class);
        return query.getResultList();
    }

    Booking findById(Long id) {
        return em.find(Booking.class, id);
    }

//      Booking findByDateAndFlightId(Long flightId, Date date) {
//        TypedQuery<Booking> namedQuery = em.createNamedQuery(Booking.FIND_BY_DATE_AND_FLIGHT_ID, Booking.class)
//                .setParameter("flightId", flightId)
//                .setParameter("bookingDate", date)
//                .setMaxResults(1);
//        List<Booking> resultList = namedQuery.getResultList();
//        return resultList.isEmpty() ? null: resultList.get(0);
//    }

//    Booking findByDateAndFlightIdAndCustomerId(Long flightId, Date date, Long customerId) {
//        TypedQuery<Booking> namedQuery = em.createNamedQuery(Booking.FIND_BY_DATE_AND_FLIGHT_ID, Booking.class)
//                .setParameter("flightId", flightId)
//                .setParameter("bookingDate", date)
//                .setParameter("customerId",customerId )
//                .setMaxResults(1);
//        List<Booking> resultList = namedQuery.getResultList();
//        return resultList.isEmpty() ? null: resultList.get(0);
//    }

    Booking create(Booking booking) throws Exception {
        log.info("BookingRepository.create() - Creating " + booking.getFlight());

        // Write the booking to the database.
        em.persist(booking);

        return booking;
    }

    Booking delete(Booking booking) throws Exception {
        log.info("BookingRepository.delete() - Deleting " + booking.getId());

        if (booking.getId() != null) {

            em.remove(em.merge(booking));

        } else {
            log.info("BookingRepository.delete() - No ID was found so can't Delete.");
        }

        return booking;
    }



}
