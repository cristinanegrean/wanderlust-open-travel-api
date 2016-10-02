package cristina.tech.blog.travel.domain;


import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotEmpty;
import org.hibernate.validator.constraints.URL;

import javax.persistence.Embeddable;
import javax.persistence.MappedSuperclass;
import javax.validation.constraints.Pattern;

@MappedSuperclass
@Embeddable
public class ContactInfo extends PostalAddress {

    private static final long serialVersionUID = 1126074635410771217L;

    @NotEmpty(message = "Contact info email cannot be null!")
    @Email
    private String email;

    @Pattern(regexp = "^\\+(?:[0-9] ?){6,14}[0-9]$")
    private String phone;

    @Pattern(regexp = "^\\+(?:[0-9] ?){6,14}[0-9]$")
    private String fax;

    @URL
    private String website;

    public ContactInfo() {
    }

    public ContactInfo(String email, String country, String postalCode) {
        super(country, postalCode);
        this.email = email;
    }

    public String getEmail() {
        return email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getFax() {
        return fax;
    }

    public void setFax(String fax) {
        this.fax = fax;
    }

    public String getWebsite() {
        return website;
    }

    public void setWebsite(String website) {
        this.website = website;
    }
}
