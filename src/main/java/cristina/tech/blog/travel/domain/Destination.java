package cristina.tech.blog.travel.domain;


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


@Entity
@Table(name = "destinations")
public class Destination extends AbstractEntity {

    private static final long serialVersionUID = 1126074635410771215L;

    @NotEmpty(message = "Destination name cannot be empty")
    @Size(min = 2, max = 100, message = "Destination name must not be longer than 100 characters and shorter than 2 characters")
    @Pattern(regexp = "[a-z-A-Z- ']*", message = "Destination name has invalid characters")
    private String name;

    @NotEmpty(message = "How to prepare when destination country is a total surprise?")
    private String country;

    private String description;

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "destination_facts", joinColumns = @JoinColumn(name = "destination", referencedColumnName = "id"))
    @Column(name = "fact")
    private List<String> facts;

    public String getName() {
        return this.name;
    }

    public String getCountry() {
        return this.country;
    }

    public String getDescription() {
        return this.description;
    }

    public List<String> getFacts() {
        return this.facts;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public void setFacts(List<String> facts) {
        this.facts = facts;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Destination() {
    }

    public Destination(String name, String country) {
        this.name = name;
        this.country = country;
    }

    public Destination(String name, String country, List<String> facts, String description) {
        this(name, country);
        this.facts = facts;
        this.description = description;
    }
}
