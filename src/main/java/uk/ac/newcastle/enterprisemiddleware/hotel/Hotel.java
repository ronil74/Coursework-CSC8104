package uk.ac.newcastle.enterprisemiddleware.hotel;

import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

public class Hotel implements Serializable {


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    private Long id;

    public String getHotelName() {
        return hotelName;
    }

    public void setHotelName(String hotelName) {
        this.hotelName = hotelName;
    }

    private String hotelName;

    public Long getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Long customerId) {
        this.customerId = customerId;
    }

    @Override
    public String toString() {
        return "Hotel{" +
                "id=" + id +
                ", hotelName='" + hotelName + '\'' +
                ", customerId=" + customerId +
                ", bookingdate=" + bookingdate +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Hotel hotel = (Hotel) o;
        return Objects.equals(id, hotel.id) && Objects.equals(hotelName, hotel.hotelName) && Objects.equals(customerId, hotel.customerId) && Objects.equals(bookingdate, hotel.bookingdate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, hotelName, customerId, bookingdate);
    }

    private Long customerId;
    private Date bookingdate;
}
