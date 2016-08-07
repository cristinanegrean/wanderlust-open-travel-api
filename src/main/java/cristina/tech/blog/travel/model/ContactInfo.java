package cristina.tech.blog.travel.model;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.Embeddable;
import javax.persistence.MappedSuperclass;
import java.util.Objects;

@MappedSuperclass
@Embeddable
public class ContactInfo extends PostalAddress {

    private static final long serialVersionUID = 1126074635410771217L;

    @NotEmpty(message = "Contact info email cannot be null!")
    @Email
    private String email;

    private String phone;

    private String fax;

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
                && Objects.equals(phone, that.phone)
                && Objects.equals(fax, that.fax)
                && Objects.equals(website, that.website)
                && super.equals(that);

    }

    @Override
    public int hashCode() {
        return Objects.hash(email, phone, fax, website, super.hashCode());
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
