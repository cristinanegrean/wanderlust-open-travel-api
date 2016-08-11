package cristina.tech.blog.travel.model;


import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.Table;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.util.List;
import java.util.Objects;


@Entity
@Table(name = "destinations")
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class Destination extends BaseEntity {

    private static final long serialVersionUID = 1126074635410771215L;

    @NotEmpty(message = "Destination name cannot be empty")
    @Size(min=2, max=100, message = "Destination name must not be longer than 100 characters and shorter than 2 characters")
    @Pattern(regexp = "[a-z-A-Z- ]*", message = "Destination name has invalid characters")
    @JsonProperty
    private String name;

    @NotEmpty(message = "How to prepare when destination country is a total surprise?")
    @JsonProperty
    private String country;

    @JsonProperty
    private String description;

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name="destination_facts", joinColumns=@JoinColumn(name="destination", referencedColumnName = "id"))
    @Column(name="fact")
    @JsonProperty
    private List<String> facts;

    protected Destination() { }

    public Destination(String name, String country) {
        this.name = name;
        this.country = country;
    }

    public Destination(String name, String country, List<String> facts, String description) {
        this(name, country);
        this.facts = facts;
        this.description = description;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Destination that = (Destination) o;
        return Objects.equals(name, that.name)
                && Objects.equals(country, that.country)
                && super.equals(that);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, country, super.hashCode());
    }

    @Override
    public String toString() {
        return "Destination{" +
                "name='" + name + '\'' +
                ", country='" + country + '\'' +
                ", description='" + description + '\'' +
                ", facts=" + facts +
                '}';
    }
}
