package cristina.tech.blog.travel.validation;


import cristina.tech.blog.travel.domain.Destination;
import cristina.tech.blog.travel.domain.Holiday;
import org.joda.time.LocalDate;
import org.junit.BeforeClass;
import org.junit.Test;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.util.HashSet;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

public class HolidayBeanValidationAnnotationTests {

    private static Validator validator;

    @BeforeClass
    public static void setUp() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    public void beanValidationAnnotationsAreChecked() {
        // no holiday data set
        Holiday holiday = new Holiday();

        Set<ConstraintViolation<Holiday>> constraintViolations = validator.validate(holiday);

        // assert validation failed
        assertThat(constraintViolations).hasSize(3);
        Set<String> constraintViolationMessages = new HashSet<>(constraintViolations.size());
        constraintViolations.forEach(hcv -> constraintViolationMessages.add(hcv.getMessage()));
        assertThat(constraintViolationMessages).contains(
                "Holiday destination cannot be null", "flightIncluded flag must be set", "accomodationIncluded flag must be set");

        // setup required fields
        holiday.setDestination(new Destination("Amsterdam", "NL"));
        holiday.setAccomodationIncluded(Boolean.TRUE);
        holiday.setFlightIncluded(Boolean.FALSE);

        // replay validation
        constraintViolations = validator.validate(holiday);

        // assert validation succeeded
        assertThat(constraintViolations).hasSize(0);

        // setup optional startsOn date as a date in the past
        holiday.setStartOn(new LocalDate().minusDays(2).toDate());

        // replay validation
        constraintViolations = validator.validate(holiday);
        assertThat(constraintViolations).hasSize(1);

        assertThat(constraintViolations.iterator().next().getMessage()).isEqualTo("Holiday startOn date must be in the future");
    }

}
