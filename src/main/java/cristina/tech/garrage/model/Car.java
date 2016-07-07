package cristina.tech.garrage.model;

import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.*;
import javax.validation.constraints.Size;



@Entity
@Table(name = "cars")
class Car {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotEmpty
    @Size(min = 2, max = 50)
    private String brand;

    @NotEmpty
    @Size(min =2, max = 50)
    private String model;

    @NotEmpty
    private String licensePlate;

    protected Car() { // Empty constructor for JPA to work
    }

    public Car(String brand, String licensePlate) {
        this.brand = brand;
        this.licensePlate = licensePlate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Car that = (Car) o;

        return licensePlate.equals(that.licensePlate);

    }

    @Override
    public int hashCode() {
        return licensePlate.hashCode();
    }
}
