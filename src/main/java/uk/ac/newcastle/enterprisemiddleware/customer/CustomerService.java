package uk.ac.newcastle.enterprisemiddleware.customer;

import uk.ac.newcastle.enterprisemiddleware.area.InvalidAreaCodeException;

import javax.enterprise.context.Dependent;
import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Singleton;
import javax.ws.rs.ClientErrorException;
import javax.ws.rs.core.Response;
import java.util.List;
import java.util.logging.Logger;


@Dependent
public class CustomerService {
    @Inject
    @Named("logger")
    Logger log;

    @Inject
    CustomerValidator validator;
    @Inject
    CustomerRepository crud;

    public List<Customer> findAllCustomers(){
        return crud.findAllCustomers();
    }

//    public List<Customer> findAllCustomersByName(String Name){
//        return crud.findAllCustomersByName(name);
//    }

    public Customer findAllCustomersById(Long id){
        return  crud.findAllCustomersById(id);
    }

    public Customer findAllCustomersByEmail(String email){
        return crud.findAllCustomersByEmail(email);
    }


    public Customer create(Customer customer) throws Exception {
        validator.validateCustomer(customer);
        try {

        } catch (ClientErrorException e) {
            if (e.getResponse().getStatusInfo() == Response.Status.NOT_FOUND) {
                throw new InvalidAreaCodeException("does not exist", e);
            } else {
                throw e;
            }
        }
        return crud.createCustomer(customer);
    }


    public Customer delete(Customer customer) {
        return crud.deleteCustomer(customer);
    }
}