package cristina.tech.blog.travel.validation;


import cristina.tech.blog.travel.domain.Destination;
import org.junit.BeforeClass;
import org.junit.Test;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.util.HashSet;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

public class DestinationBeanValidationAnnotationTests {

    private static Validator validator;

    @BeforeClass
    public static void setUp() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    public void beanValidationAnnotationsAreChecked() {
        // no destination data set
        Destination destination = new Destination();

        Set<ConstraintViolation<Destination>> constraintViolations = validator.validate(destination);

        // assert name and country validation failed
        assertThat(constraintViolations).hasSize(2);
        Set<String> constraintViolationMessages = new HashSet<>(constraintViolations.size());
        constraintViolations.forEach(dcv -> constraintViolationMessages.add(dcv.getMessage()));
        assertThat(constraintViolationMessages).contains(
                "Destination name cannot be empty", "How to prepare when destination country is a total surprise?");

        // setup valid country ISO2
        destination.setCountry("NL");

        // setup destination name too short and with invalid characters
        destination.setName("@");

        // replay validation
        constraintViolations = validator.validate(destination);

        // assert name validation failed
        assertThat(constraintViolations).hasSize(2);

        // rebuild messages
        Set<String> replayConstraintViolationMessages = new HashSet<>(constraintViolations.size());
        constraintViolations.forEach(dcv -> replayConstraintViolationMessages.add(dcv.getMessage()));

        // assert new messages are reported for destination name @
        assertThat(replayConstraintViolationMessages).contains(
                "Destination name has invalid characters", "Destination name must not be longer than 100 characters and shorter than 2 characters");

        // setup valid destination name
        destination.setName("'s-Hertogenbosch");

        // validate again
        constraintViolations = validator.validate(destination);

        // assert destination validation passed
        assertThat(constraintViolations).hasSize(0);
    }

}
