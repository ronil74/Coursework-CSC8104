package uk.ac.newcastle.enterprisemiddleware.taxi;

import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

public class Taxi implements Serializable {
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    private Long id;


    public String getTaxiRegistrationNumber() {
        return taxiRegistrationNumber;
    }

    public void setTaxiRegistrationNumber(String taxiRegistrationNumber) {
        this.taxiRegistrationNumber = taxiRegistrationNumber;
    }

    private String taxiRegistrationNumber;

    public Long getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Long customerId) {
        this.customerId = customerId;
    }

    @Override
    public String toString() {
        return "Taxi{" +
                "id=" + id +
                ", taxiRegistrationNumber='" + taxiRegistrationNumber + '\'' +
                ", customerId=" + customerId +
                ", bookingDate=" + bookingDate +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Taxi taxi = (Taxi) o;
        return Objects.equals(id, taxi.id) && Objects.equals(taxiRegistrationNumber, taxi.taxiRegistrationNumber) && Objects.equals(customerId, taxi.customerId) && Objects.equals(bookingDate, taxi.bookingDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, taxiRegistrationNumber, customerId, bookingDate);
    }

    private Long customerId;

    public Date getBookingDate() {
        return bookingDate;
    }

    public void setBookingDate(Date bookingDate) {
        this.bookingDate = bookingDate;
    }

    private Date bookingDate;


}
