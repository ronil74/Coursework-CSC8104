package uk.ac.newcastle.enterprisemiddleware.travelAgent;
import uk.ac.newcastle.enterprisemiddleware.customer.Customer;
import uk.ac.newcastle.enterprisemiddleware.flight.*;
import uk.ac.newcastle.enterprisemiddleware.hotel.*;
import java.io.Serializable;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import javax.validation.ConstraintViolationException;
import java.util.List;
import java.util.logging.Logger;

@RequestScoped
public class TravelAgentRepository {
    @Inject
    @Named("logger")
    Logger log;

    @Inject
    EntityManager em;


    List<TravelAgentBooking> findByCustomer(Long customerId) {
        TypedQuery<TravelAgentBooking> query = em.createNamedQuery(TravelAgentBooking.FIND_BY_CUSTOMER, TravelAgentBooking.class).setParameter("customerId", customerId);
        return query.getResultList();
    }

    List<TravelAgentBooking> findAll() {
        TypedQuery<TravelAgentBooking> query = em.createNamedQuery(TravelAgentBooking.FIND_ALL, TravelAgentBooking.class);
        return query.getResultList();
    }

    public TravelAgentBooking findById(Long id) {
        return em.find(TravelAgentBooking.class, id);
    }

    TravelAgentBooking create(TravelAgentBooking travelAgentBooking) throws Exception {
        em.persist(travelAgentBooking); //add to database
        return travelAgentBooking;
    }

    TravelAgentBooking delete(TravelAgentBooking travelAgentBooking) throws Exception {

        if (travelAgentBooking.getId() != null) {
            em.remove(em.merge(travelAgentBooking)); //delete from database
        } else {
            log.info("TravelAgentRepository.delete() - No ID was found so can't Delete.");
        }

        return travelAgentBooking;
    }


}
