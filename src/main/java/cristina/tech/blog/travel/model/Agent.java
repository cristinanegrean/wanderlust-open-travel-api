package cristina.tech.blog.travel.model;

import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.*;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name="travel_agents")
/** Models an Open Travel Wanderlust API Agent. An agent offers one of multiple {@link Holiday} packages. */
public class Agent extends BaseEntity {
    private static final long serialVersionUID = 1126074635410771219L;

    @NotEmpty(message = "Travel agent name cannot be empty!")
    private String name;

    @Embedded
    private ContactInfo contactInfo;

    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, targetEntity = Holiday.class)
    @JoinTable(name = "travel_agent_holiday_packages",
        joinColumns = @JoinColumn(name = "travel_agent", referencedColumnName = "id"),
        inverseJoinColumns = @JoinColumn(name = "holiday_package"))
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
        return Objects.equals(name, that.name);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(name);
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