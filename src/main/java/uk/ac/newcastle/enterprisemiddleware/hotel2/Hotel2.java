package uk.ac.newcastle.enterprisemiddleware.hotel2;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
/**
 * <p>Simple POJO representing AreaCode objects</p>
 *
 * @author Ronil
 */

public class Hotel2 implements Serializable {
    private static final long serialVersionUID = 1L;

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

    @NotNull
    private String hotelName;

    public String getPhonenumber() {
        return phonenumber;
    }

    public void setPhonenumber(String phonenumber) {
        this.phonenumber = phonenumber;
    }

    @NotNull
    private String phonenumber;


    public String getPostcode() {
        return postcode;
    }

    public void setPostcode(String postcode) {
        this.postcode = postcode;
    }

    @NotNull
    private String postcode;
}
