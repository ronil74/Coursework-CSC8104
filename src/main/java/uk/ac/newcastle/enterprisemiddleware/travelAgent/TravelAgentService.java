package uk.ac.newcastle.enterprisemiddleware.travelAgent;

import uk.ac.newcastle.enterprisemiddleware.customer.Customer;

import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Singleton;
import javax.persistence.TypedQuery;
import java.util.List;
import java.util.logging.Logger;
@Singleton

public class TravelAgentService {
    @Inject
    @Named("logger")
    Logger log;

    @Inject
    TravelAgentRepository travelAgentRepository;
    public List<TravelAgentBooking> findAll() {
        return travelAgentRepository.findAll();
    }

    public TravelAgentBooking findById(Long id) {
        return travelAgentRepository.findById(id);
    }

//    public List<TravelAgentBooking> findByCustomer(Long customerId) {
//        return travelAgentRepository.findByCustomer(customerId);
//    }

    public TravelAgentBooking create(TravelAgentBooking booking) throws Exception {
        return travelAgentRepository.create(booking);
    }

    public TravelAgentBooking delete(TravelAgentBooking booking) throws Exception {
        return travelAgentRepository.delete(booking);
    }

}
