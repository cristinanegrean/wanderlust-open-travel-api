package cristina.tech.blog.travel.model;


import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.CascadeType;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.validation.Valid;
import java.util.Objects;
import java.util.Set;

/** Models an Open Travel Wanderlust API Agent. An agent offers one of multiple {@link Holiday} packages. */
@Entity
@Table(name="travel_agents")
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class Agent extends BaseEntity {
    private static final long serialVersionUID = 1126074635410771219L;

    @NotEmpty(message = "Travel agent name cannot be empty!")
    @JsonProperty
    private String name;

    @Embedded
    @Valid
    @JsonProperty
    private ContactInfo contactInfo;

    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, targetEntity = Holiday.class)
    @JoinTable(name = "travel_agent_holiday_packages",
        joinColumns = @JoinColumn(name = "travel_agent", referencedColumnName = "id"),
        inverseJoinColumns = @JoinColumn(name = "holiday_package"))
    @Valid
    @JsonProperty
    private Set<Holiday> holidays;

    public Agent() { }

    public Agent(String name, ContactInfo contactInfo, Set<Holiday> holidays) {
        this.name = name;
        this.contactInfo = contactInfo;
        this.holidays = holidays;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Agent that = (Agent) o;
        return Objects.equals(name, that.name) && super.equals(that);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, super.hashCode());
    }

    @Override
    public String toString() {
        return "Agent{" +
                "name='" + name + '\'' +
                ", contactInfo=" + contactInfo +
                ", holidays=" + holidays +
                '}';
    }
}
