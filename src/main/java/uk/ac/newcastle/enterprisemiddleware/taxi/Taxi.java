package uk.ac.newcastle.enterprisemiddleware.taxi;

import uk.ac.newcastle.enterprisemiddleware.booking.*;
import uk.ac.newcastle.enterprisemiddleware.customer.Customer;


import javax.validation.constraints.NotNull;
import java.io.Serializable;
public class Taxi implements Serializable {
    private static final long serialVersionUID = 1L;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    private Long id;


    public Long getTaxiId() {
        return taxiId;
    }

    public void setTaxiId(Long taxiId) {
        this.taxiId = taxiId;
    }

    @NotNull
    private Long taxiId;

    public String getRegistration() {
        return Registration;
    }

    public void Registration(String taxiRegistration) {
        this.Registration = Registration;
    }

    @NotNull
    private String Registration;


    public int getNoOfSeats() {
        return noOfSeats;
    }

    public void setNoOfSeats(int noOfSeats) {
        this.noOfSeats = noOfSeats;
    }

    @NotNull
    private int noOfSeats;






}