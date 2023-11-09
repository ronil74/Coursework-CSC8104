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


    public String getTaxiId() {
        return taxiId;
    }

    public void setTaxiId(String taxiId) {
        this.taxiId = taxiId;
    }

    @NotNull
    private String taxiId;

    public String getTaxiRegistration() {
        return taxiRegistration;
    }

    public void setTaxiRegistration(String taxiRegistration) {
        this.taxiRegistration = taxiRegistration;
    }

    @NotNull
    private String taxiRegistration;


    public int getNoOfSeats() {
        return noOfSeats;
    }

    public void setNoOfSeats(int noOfSeats) {
        this.noOfSeats = noOfSeats;
    }

    @NotNull
    private int noOfSeats;






}