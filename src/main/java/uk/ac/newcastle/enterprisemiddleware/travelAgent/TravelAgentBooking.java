package uk.ac.newcastle.enterprisemiddleware.travelAgent;

import com.fasterxml.jackson.annotation.JsonIgnore;
import uk.ac.newcastle.enterprisemiddleware.booking.Booking;

import javax.persistence.*;
import javax.validation.constraints.*;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;
import java.util.Date;
import java.util.Objects;


@Entity
@NamedQueries({
        @NamedQuery(name = TravelAgentBooking.FIND_ALL, query = "SELECT c FROM TravelAgentBooking c ORDER BY c.id"),
        @NamedQuery(name = TravelAgentBooking.Find_BY_ID, query = "SELECT c FROM TravelAgentBooking c WHERE c.id = :id")
})
@XmlRootElement
@Table(name = "TravelAgentBooking", uniqueConstraints = @UniqueConstraint(columnNames = "id"))
public class TravelAgentBooking implements Serializable{

    private static final long serialVersionUID = 1L;
    public static final String FIND_ALL = "TravelAgentBooking.findAll";

    public static final String Find_BY_ID="TravelAgentBooking.findById";

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

//    public Long getCustomerId() {
//        return customerId;
//    }
//
//    public void setCustomerId(Long customerId) {
//        this.customerId = customerId;
//    }
//
//    @Column(name = "customerId")
//    private Long customerId;

    public int getHotelId() {
        return hotelId;
    }

    public void setHotelId(int hotelId) {
        this.hotelId = hotelId;
    }

    @Column(name = "hotelId")
    private int hotelId;

    public int getTaxiId() {
        return taxiId;
    }

    public void setTaxiId(int taxiId) {
        this.taxiId = taxiId;
    }

    @Column(name = "taxiId")
    private int taxiId;


//    public int getHotel2Id() {
//        return hotel2Id;
//    }
//
//    public void setHotel2Id(int hotel2Id) {
//        this.hotel2Id = hotel2Id;
//    }
//
//    private int hotel2Id;

    public Long getFlightId() {
        return flightId;
    }

    public void setFlightId(Long flightId) {
        this.flightId = flightId;
    }

    @Column(name = "flightId")
    private Long flightId;

    public Booking getBooking() {
        return booking;
    }

    public void setBooking(Booking booking) {
        this.booking = booking;
    }

    @JsonIgnore
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn
    private Booking booking;


    public Date getBookingDate() {
        return bookingDate;
    }

    public void setBookingDate(Date bookingDate) {
        this.bookingDate = bookingDate;
    }

    @Column(name="bookingDate")
    private Date bookingDate;



}
