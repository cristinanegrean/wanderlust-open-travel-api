package cristina.tech.blog.travel.domain;


import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotEmpty;
import org.hibernate.validator.constraints.URL;

import javax.persistence.Embeddable;
import javax.persistence.MappedSuperclass;
import javax.validation.constraints.Pattern;

@MappedSuperclass
@Embeddable
@Data
@NoArgsConstructor(force = true)
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

    public ContactInfo(String email, String country, String postalCode) {
        super(country, postalCode);
        this.email = email;
    }
}
