package uk.ac.newcastle.enterprisemiddleware.customer;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Singleton;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.util.List;
import java.util.logging.Logger;



@RequestScoped
public class CustomerRepository {

    @Inject
    @Named("logger")
    Logger log;

    @Inject
    EntityManager em;

    List<Customer> findAllCustomers() {
        TypedQuery<Customer> namedQuery = em.createNamedQuery(Customer.FIND_ALL, Customer.class);
        return namedQuery.getResultList();
    }

//    List<Customer> findAllCustomersByName(String name) {
//        TypedQuery<Customer> namedQuery = em
//                .createNamedQuery(Customer.FIND_BY_NAME, Customer.class)
//                .setParameter("name", name)
//                .setMaxResults(1);
//        List<Customer> resultList = namedQuery.getResultList();
//        return resultList.isEmpty() ? null: resultList.get(0);
//    }
//
    /**
     * <p>Returns a single Customer object, specified by a Long id.<p/>
     *
     * @param id The id field of the Customer to be returned
     * @return The Customer with the specified id
     */
     Customer findAllCustomersById(Long id) {
        return em.find(Customer.class, id);
    }
//
    Customer findAllCustomersByEmail(String email) {
        TypedQuery<Customer> query = em.createNamedQuery(Customer.FIND_BY_EMAIL, Customer.class).setParameter("email", email);
        return query.getSingleResult();
    }
//
    Customer createCustomer(Customer customer) {
        em.persist(customer);
        return customer;
    }
//
    Customer deleteCustomer(Customer customer) {
        if(customer.getId() != null && customer.getId() > 0) {
            em.remove(customer);
        } else {
            log.info("CustomerRepository.deleteById() - No ID was found so can't Delete.");
        }
        return customer;
    }

}