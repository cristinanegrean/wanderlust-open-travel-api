package cristina.tech.blog.travel.domain;


import com.fasterxml.jackson.annotation.JsonUnwrapped;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.*;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.Set;

/**
 * Models an Open Travel Wanderlust API Agent. An agent offers one of multiple {@link Holiday} packages.
 */
@Entity
@Table(name = "travel_agents")
@Data
@NoArgsConstructor(force = true)
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

    public Agent(String name) {
        this.name = name;
    }
}
