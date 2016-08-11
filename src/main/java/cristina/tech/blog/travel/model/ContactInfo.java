package cristina.tech.blog.travel.model;


import com.fasterxml.jackson.annotation.JsonProperty;
import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotEmpty;
import org.hibernate.validator.constraints.URL;

import javax.persistence.Embeddable;
import javax.persistence.MappedSuperclass;
import javax.validation.constraints.Pattern;
import java.util.Objects;

@MappedSuperclass
@Embeddable
public class ContactInfo extends PostalAddress {

    private static final long serialVersionUID = 1126074635410771217L;

    @NotEmpty(message = "Contact info email cannot be null!")
    @Email
    @JsonProperty
    private String email;

    @Pattern(regexp="(^$|[0-9]{10})")
    @JsonProperty
    private String phone;

    @Pattern(regexp="(^$|[0-9]{10})")
    @JsonProperty
    private String fax;

    @JsonProperty
    @URL
    private String website;

    public ContactInfo() { }

    public ContactInfo(String email, String phone, String fax, String website) {
        this.email = email;
        this.phone = phone;
        this.fax = fax;
        this.website = website;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ContactInfo that = (ContactInfo) o;

        return Objects.equals(email, that.email)
                && super.equals(that);

    }

    @Override
    public int hashCode() {
        return Objects.hash(email, super.hashCode());
    }

    @Override
    public String toString() {
        return "ContactInfo{" +
                "email='" + email + '\'' +
                ", phone='" + phone + '\'' +
                ", fax='" + fax + '\'' +
                ", website='" + website + '\'' +
                '}';
    }
}
