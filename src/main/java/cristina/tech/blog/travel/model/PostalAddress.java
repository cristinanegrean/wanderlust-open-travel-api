package cristina.tech.blog.travel.model;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonInclude;
import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.Embeddable;
import javax.persistence.MappedSuperclass;
import java.io.Serializable;
import java.util.Objects;

@MappedSuperclass
@Embeddable
@JsonInclude(JsonInclude.Include.NON_EMPTY)
@JsonAutoDetect(fieldVisibility= JsonAutoDetect.Visibility.ANY)
public class PostalAddress implements Serializable {

    private static final long serialVersionUID = 1126074635410771214L;

    @NotEmpty(message = "Postal address country cannot be null!")
    protected String country;

    protected String city;

    protected String postalCode;

    protected String streetAddress;

    public PostalAddress() { }

    public PostalAddress(String country, String city, String postalCode, String streetAddress) {
        this.country = country;
        this.city = city;
        this.postalCode = postalCode;
        this.streetAddress = streetAddress;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        PostalAddress that = (PostalAddress) o;

        return Objects.equals(country, that.country)
                && Objects.equals(city, that.city)
                && Objects.equals(postalCode, that.postalCode)
                && Objects.equals(streetAddress, that.postalCode);

    }

    @Override
    public int hashCode() {
        return Objects.hash(country, city, postalCode, streetAddress);
    }

    @Override
    public String toString() {
        return "PostalAddress{" +
                "country='" + country + '\'' +
                ", city='" + city + '\'' +
                ", postalCode='" + postalCode + '\'' +
                ", streetAddress='" + streetAddress + '\'' +
                '}';
    }
}
