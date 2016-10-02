package cristina.tech.blog.travel.domain;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonTypeId;
import org.hibernate.annotations.Type;
import org.joda.time.DateTime;
import org.joda.time.chrono.ISOChronology;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Locale;

@MappedSuperclass
@JsonIgnoreProperties({"createdAt", "modifiedAt"})
@JsonInclude(JsonInclude.Include.NON_EMPTY)
@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
public abstract class BaseEntity implements Serializable {

    private static final long serialVersionUID = 1126074635410771212L;
    protected static final DateTimeFormatter DTF = DateTimeFormat.forPattern("yyyy-MM-dd'T'HH:mm:ss.SSSZZ")
            .withLocale(Locale.ROOT).withChronology(ISOChronology.getInstanceUTC());

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonTypeId
    protected Integer id;

    @Column(name = "created_at", nullable = false)
    @Type(type = "cristina.tech.blog.travel.domain.PersistentDateTime")
    protected DateTime createdAt;

    @Column(name = "modified_at", nullable = false)
    @Type(type = "cristina.tech.blog.travel.domain.PersistentDateTime")
    protected DateTime modifiedAt = new DateTime();

    @PrePersist()
    @PreUpdate()
    public void prePersist() {
        DateTime now = new DateTime();
        if (createdAt == null) {
            createdAt = now;
        }
        modifiedAt = now;
    }

    protected BaseEntity() {
    }

    public Integer getId() {
        return id;
    }
}
