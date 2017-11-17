package cristina.tech.blog.travel.domain;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonInclude;
import cristina.tech.blog.travel.validation.CountryPostalCode;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.Embeddable;
import javax.persistence.MappedSuperclass;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@MappedSuperclass
@Embeddable
@JsonInclude(JsonInclude.Include.NON_EMPTY)
@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
@CountryPostalCode(country = "country", postalCode = "postalCode")
@Data
@NoArgsConstructor(force=true)
public class PostalAddress implements Serializable {

    private static final long serialVersionUID = 1126074635410771214L;

    @NotEmpty(message = "Postal address country cannot be null!")
    protected String country;

    protected String city;

    @NotNull
    protected String postalCode;

    protected String streetAddress;

    public PostalAddress(String country, String postalCode) {
        this.country = country;
        this.postalCode = postalCode;
    }
}
