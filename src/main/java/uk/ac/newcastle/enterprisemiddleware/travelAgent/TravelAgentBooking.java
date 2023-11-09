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
@NamedQueries({@NamedQuery(name = TravelAgentBooking.FIND_ALL, query = "SELECT c FROM TravelAgentBooking c ORDER BY c.customerId"),
        @NamedQuery(name = TravelAgentBooking.FIND_BY_CUSTOMER, query = "SELECT c FROM TravelAgentBooking c WHERE c.customerId = :customerId"),
        @NamedQuery(name = TravelAgentBooking.Find_BY_ID, query = "SELECT c FROM TravelAgentBooking c WHERE c.id = :id")
})
@XmlRootElement
@Table(name = "TravelAgentBooking", uniqueConstraints = @UniqueConstraint(columnNames = "id"))
public class TravelAgentBooking implements Serializable{

    private static final long serialVersionUID = 1L;
    public static final String FIND_ALL = "TravelAgentBooking.findAll";
    public static final String FIND_BY_CUSTOMER = "TravelAgentBooking.findByCustomer";

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

    public Long getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Long customerId) {
        this.customerId = customerId;
    }

    @Column(name = "customerId")
    private Long customerId;

    public Long getHotelId() {
        return hotelId;
    }

    public void setHotelId(Long hotelId) {
        this.hotelId = hotelId;
    }

    @Column(name = "hotelId")
    private Long hotelId;

    public Long getTaxiId() {
        return taxiId;
    }

    public void setTaxiId(Long taxiId) {
        this.taxiId = taxiId;
    }

    @Column(name = "taxiId")
    private Long taxiId;

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



}
