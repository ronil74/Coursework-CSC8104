package uk.ac.newcastle.enterprisemiddleware.hotel;

import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

public class HotelBooking implements Serializable {

    private static final long serialVersionUID = 1L;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    private Long id;

//    public String getHotelName() {
//        return hotelName;
//    }
//
//    public void setHotelName(String hotelName) {
//        this.hotelName = hotelName;
//    }
//
//    private String hotelName;

    public Long getHotelId() {
        return hotelId;
    }

    public void setHotelId(Long hotelId) {
        this.hotelId = hotelId;
    }

    private Long hotelId;

    public Long getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Long customerId) {
        this.customerId = customerId;
    }



    private Long customerId;

    public Date getBookingdate() {
        return bookingdate;
    }

    public void setBookingdate(Date bookingdate) {
        this.bookingdate = bookingdate;
    }

    private Date bookingdate;

}
