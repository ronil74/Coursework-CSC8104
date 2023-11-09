package uk.ac.newcastle.enterprisemiddleware.hotel;
import uk.ac.newcastle.enterprisemiddleware.booking.*;
import uk.ac.newcastle.enterprisemiddleware.customer.Customer;


import javax.validation.constraints.NotNull;
import java.io.Serializable;

public class Hotel implements Serializable {
    private static final long serialVersionUID = 1L;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    private Long id;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @NotNull
    private String name;

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
