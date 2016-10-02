package cristina.tech.blog.travel.domain;


import com.fasterxml.jackson.annotation.JsonFormat;
import org.hibernate.validator.constraints.Range;

import javax.persistence.*;
import javax.validation.constraints.Future;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.Date;

/**
 * Models an Open Travel Wanderlust API Holiday.
 * A Holiday has for now one {@link Destination} only and it is offered by one or many travel {@link Agent}(s).
 */
@Entity
@Table(name = "holiday_packages")
public class Holiday extends BaseEntity {
    private static final long serialVersionUID = 1126074635410771213L;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "destination")
    @NotNull(message = "Holiday destination cannot be null")
    private Destination destination;

    @Future(message = "Holiday startOn date must be in the future")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd", timezone = "CET")
    private Date startOn;

    @Range(min = 3, max = 90)
    private Integer daysCount;

    private String departFrom;

    private BigDecimal price;

    @NotNull(message = "flightIncluded flag must be set")
    private Boolean flightIncluded;

    @NotNull(message = "accomodationIncluded flag must be set")
    private Boolean accomodationIncluded;

    private String packageInfo;

    public Holiday() {
    }

    public Holiday(Destination destination, Boolean flightIncluded, Boolean accomodationIncluded) {
        this.destination = destination;
        this.flightIncluded = flightIncluded;
        this.accomodationIncluded = accomodationIncluded;
    }

    public void setDestination(Destination destination) {
        this.destination = destination;
    }

    public void setStartOn(Date startOn) {
        this.startOn = startOn;
    }

    public void setDaysCount(Integer daysCount) {
        this.daysCount = daysCount;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public void setPackageInfo(String packageInfo) {
        this.packageInfo = packageInfo;
    }

    public void setDepartFrom(String departFrom) {
        this.departFrom = departFrom;
    }

    public void setFlightIncluded(Boolean flightIncluded) {
        this.flightIncluded = flightIncluded;
    }

    public void setAccomodationIncluded(Boolean accomodationIncluded) {
        this.accomodationIncluded = accomodationIncluded;
    }

    public Destination getDestination() {
        return destination;
    }

    public Date getStartOn() {
        return startOn;
    }

    public Integer getDaysCount() {
        return daysCount;
    }

    public String getDepartFrom() {
        return departFrom;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public Boolean getFlightIncluded() {
        return flightIncluded;
    }

    public Boolean getAccomodationIncluded() {
        return accomodationIncluded;
    }

    public String getPackageInfo() {
        return packageInfo;
    }
}
