package uk.ac.newcastle.enterprisemiddleware.customer;

import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Singleton;
import java.util.List;
import java.util.logging.Logger;


@Singleton
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


    public Customer create(Customer customer) {
        return crud.createCustomer(customer);
    }


    public Customer delete(Customer customer) {
        return crud.deleteCustomer(customer);
    }
}