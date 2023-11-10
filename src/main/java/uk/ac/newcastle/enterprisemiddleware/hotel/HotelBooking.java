package uk.ac.newcastle.enterprisemiddleware.hotel;

import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

public class HotelBooking implements Serializable {

    private static final long serialVersionUID = 1L;


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    private int id;

//    public String getHotelName() {
//        return hotelName;
//    }
//
//    public void setHotelName(String hotelName) {
//        this.hotelName = hotelName;
//    }
//
//    private String hotelName;

    public int getHotelId() {
        return hotelId;
    }

    public void setHotelId(int hotelId) {
        this.hotelId = hotelId;
    }

    private int hotelId;

    public int getCustomerId() {
        return customerId;
    }

    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }



    private int customerId;

    public Date getBooking_Date() {
        return booking_Date;
    }

    public void setBooking_Date(Date booking_Date) {
        this.booking_Date = booking_Date;
    }

    private Date booking_Date;

}
