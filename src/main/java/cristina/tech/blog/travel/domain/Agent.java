package cristina.tech.blog.travel.domain;


import com.fasterxml.jackson.annotation.JsonUnwrapped;
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
import javax.validation.constraints.NotNull;
import java.util.Set;

/**
 * Models an Open Travel Wanderlust API Agent. An agent offers one of multiple {@link Holiday} packages.
 */
@Entity
@Table(name = "travel_agents")
public class Agent extends AbstractEntity {
    private static final long serialVersionUID = 1126074635410771219L;

    @NotEmpty(message = "Travel agent name cannot be empty!")
    private String name;

    @Embedded
    @NotNull(message = "Travel agent contact info is mandatory")
    @Valid
    @JsonUnwrapped
    private ContactInfo contactInfo;

    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, targetEntity = Holiday.class)
    @JoinTable(name = "travel_agent_holiday_packages",
            joinColumns = @JoinColumn(name = "travel_agent", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "holiday_package"))
    private Set<Holiday> holidays;

    /** Default C-tor needed by Jackson JSON. */
    public Agent() {
    }

    public Agent(String name) {
        this.name = name;
    }

    /** Getters and setters used by unit and integration tests. */
    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setHolidays(Set<Holiday> holidays) {
        this.holidays = holidays;
    }

    public ContactInfo getContactInfo() {
        return contactInfo;
    }

    public void setContactInfo(ContactInfo contactInfo) {
        this.contactInfo = contactInfo;
    }

}
