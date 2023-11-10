package uk.ac.newcastle.enterprisemiddleware.taxi;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

public class TaxiBooking implements Serializable {

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


    private Long taxiId;

    public Long getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Long customerId) {
        this.customerId = customerId;
    }

    @Override
    public String toString() {
        return "TaxiBooking{" +
                "id=" + id +
                ", taxiId='" + taxiId + '\'' +
                ", customerId=" + customerId +
                ", bookingDate=" + bookingDate +
                '}';
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
