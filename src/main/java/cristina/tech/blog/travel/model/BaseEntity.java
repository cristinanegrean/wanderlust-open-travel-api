package cristina.tech.blog.travel.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Type;
import org.joda.time.DateTime;
import org.joda.time.chrono.ISOChronology;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Locale;
import java.util.Objects;

@MappedSuperclass
public abstract class BaseEntity implements Serializable {

    private static final long serialVersionUID = 1126074635410771212L;
    protected static final DateTimeFormatter DTF = DateTimeFormat.forPattern("yyyy-MM-dd'T'HH:mm:ss.SSSZZ").withLocale(Locale.ROOT).withChronology(ISOChronology.getInstanceUTC());

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonIgnore
    protected Integer id;

    @Column(name = "created_at", nullable = false)
    @Type(type="cristina.tech.blog.travel.model.PersistentDateTime")
    @JsonIgnore
    protected DateTime createdAt;

    @Column(name = "modified_at", nullable = false)
    @Type(type="cristina.tech.blog.travel.model.PersistentDateTime")
    @JsonIgnore
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

    protected BaseEntity(Integer uid) {
        this.id = uid;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public DateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(DateTime createdAt) {
        this.createdAt = createdAt;
    }

    public DateTime getModifiedAt() {
        return modifiedAt;
    }

    public void setModifiedAt(DateTime modifiedAt) {
        this.modifiedAt = modifiedAt;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;

        BaseEntity that = (BaseEntity) obj;

        // if id is null we only check for object identity
        if (getId() == null || that.getId() == null) {
            return Objects.equals(createdAt, that.getCreatedAt())
                    && Objects.equals(modifiedAt, that.getModifiedAt());

        }

        return getId().equals(that.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, createdAt, modifiedAt);
    }

    @Override
    public String toString() {
        return "BaseEntity{" +
                "id=" + id +
                ", createdAt=" + createdAt +
                ", modifiedAt=" + modifiedAt +
                '}';
    }
}
