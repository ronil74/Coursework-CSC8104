package uk.ac.newcastle.enterprisemiddleware.hotel2;

import java.io.Serializable;
import java.util.Date;
/**
 * <p>Simple POJO representing HotelBooking objects</p>
 *
 * @author Ronil
 */

public class HotelBooking2 implements Serializable {

    private static final long serialVersionUID = 1L;
    private int id;
    private int hotelId;
    private int customerId;

//    public String getHotelName() {
//        return hotelName;
//    }
//
//    public void setHotelName(String hotelName) {
//        this.hotelName = hotelName;
//    }
//
//    private String hotelName;
    private Date bookingDate;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getHotelId() {
        return hotelId;
    }

    public void setHotelId(int hotelId) {
        this.hotelId = hotelId;
    }

    public int getCustomerId() {
        return customerId;
    }

    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }

    public Date getBookingDate() {
        return bookingDate;
    }

    public void setBookingDate(Date bookingDate) {
        this.bookingDate = bookingDate;
    }

}