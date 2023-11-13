package uk.ac.newcastle.enterprisemiddleware.taxi;

import uk.ac.newcastle.enterprisemiddleware.customer.Customer;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

public class TaxiBooking implements Serializable {

    private static final long serialVersionUID = 1L;
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    private int id;


    public int getTaxiId() {
        return taxiId;
    }

    public void setTaxiId(int taxiId) {
        this.taxiId = taxiId;
    }


    private int taxiId;




    public int getCustomerId() {
        return customerId;
    }

    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }
    private int customerId;
//
////    @Override
////    public String toString() {
////        return "TaxiBooking{" +
////                "id=" + id +
////                ", taxiId='" + taxiId + '\'' +
////                ", customerId=" + customerId +
////                ", bookingDate=" + bookingDate +
////                '}';
////    }
//
//    private Long customerId;

    public Date getBookingDate() {
        return bookingDate;
    }

    public void setBookingDate(Date bookingDate) {
        this.bookingDate = bookingDate;
    }

    private Date bookingDate;


}
