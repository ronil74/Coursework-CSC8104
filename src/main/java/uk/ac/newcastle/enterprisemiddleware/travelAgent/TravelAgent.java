package uk.ac.newcastle.enterprisemiddleware.travelAgent;
import io.smallrye.common.constraint.NotNull;
import uk.ac.newcastle.enterprisemiddleware.booking.*;
import uk.ac.newcastle.enterprisemiddleware.customer.Customer;
import uk.ac.newcastle.enterprisemiddleware.flight.*;
import uk.ac.newcastle.enterprisemiddleware.hotel.*;
import uk.ac.newcastle.enterprisemiddleware.hotel2.HotelBooking2;
import uk.ac.newcastle.enterprisemiddleware.taxi.TaxiBooking;

import java.io.Serializable;
import java.util.Date;

public class TravelAgent implements Serializable {
    private static final long serialVersionUID = 1L;

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    @NotNull
    private Customer customer;


    public Booking getFlight() {
        return flight;
    }

    public void setFlight(Booking flight) {
        this.flight = flight;
    }

    @NotNull
    private Booking flight;


    public HotelBooking getHotelBooking() {
        return hotelBooking;
    }

    public void setHotelBooking(HotelBooking hotelBooking) {
        this.hotelBooking = hotelBooking;
    }

    @NotNull
    private HotelBooking hotelBooking;


//    public TaxiBooking getTaxiBooking() {
//        return taxiBooking;
//    }
//
//    public void setTaxiBooking(TaxiBooking taxiBooking) {
//        this.taxiBooking = taxiBooking;
//    }
//
//    @NotNull
//    private TaxiBooking taxiBooking;


    public HotelBooking2 getHotelBooking2() {
        return hotelBooking2;
    }

    public void setHotelBooking2(HotelBooking2 hotelBooking2) {
        this.hotelBooking2 = hotelBooking2;
    }

    private HotelBooking2 hotelBooking2;

    public Date getBookingDate() {
        return bookingDate;
    }

    public void setBookingDate(Date bookingDate) {
        this.bookingDate = bookingDate;
    }

    @NotNull
    private Date bookingDate;




}
