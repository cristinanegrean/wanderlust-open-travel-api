package cristina.tech.blog.travel.validation;


import cristina.tech.blog.travel.domain.PostalAddress;
import org.junit.BeforeClass;
import org.junit.Test;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

public class CountryPostalCodeCustomValidatorTests {

    private static Validator validator;
    private static final String ERR_MESSAGE = "Invalid postal code for country";

    @BeforeClass
    public static void setUp() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    public void invalidNLPostalCodeFormats() {
        // invalid postal code format for NL
        PostalAddress postalAddress = new PostalAddress("NL", "1234");

        // validate
        Set<ConstraintViolation<PostalAddress>> constraintViolations = validator.validate(postalAddress);
        assertThat(constraintViolations).hasSize(1);
        assertThat(constraintViolations.iterator().next().getMessage()).isEqualTo(ERR_MESSAGE);

        postalAddress.setPostalCode("3825G");

        // replay validation
        constraintViolations = validator.validate(postalAddress);
        assertThat(constraintViolations).hasSize(1);
        assertThat(constraintViolations.iterator().next().getMessage()).isEqualTo(ERR_MESSAGE);

        postalAddress.setPostalCode("3825 G");

        // replay validation
        constraintViolations = validator.validate(postalAddress);
        assertThat(constraintViolations).hasSize(1);
        assertThat(constraintViolations.iterator().next().getMessage()).isEqualTo(ERR_MESSAGE);

        postalAddress.setPostalCode("3825GPJ");

        // replay validation
        constraintViolations = validator.validate(postalAddress);
        assertThat(constraintViolations).hasSize(1);
        assertThat(constraintViolations.iterator().next().getMessage()).isEqualTo(ERR_MESSAGE);
    }

    @Test
    public void validNLPostalCodeFormats() {
        PostalAddress postalAddressInAmersfoort = new PostalAddress("NL", "3825GP");
        PostalAddress postalAddressInAmsterdam = new PostalAddress("NL", "1071 LN");

        // validate one
        Set<ConstraintViolation<PostalAddress>> constraintViolations = validator.validate(postalAddressInAmersfoort);
        assertThat(constraintViolations).hasSize(0);

        // validate two
        constraintViolations = validator.validate(postalAddressInAmsterdam);
        assertThat(constraintViolations).hasSize(0);
    }

    @Test
    public void unsupportedCountryGivesWrongMesssage() {
        PostalAddress unsupportedCountry = new PostalAddress("RU", "101000");

        // valid combination but app does not support country ISO2 RU yet
        Set<ConstraintViolation<PostalAddress>> constraintViolations = validator.validate(unsupportedCountry);
        assertThat(constraintViolations).hasSize(1);
        assertThat(constraintViolations.iterator().next().getMessage()).isEqualTo(ERR_MESSAGE);
    }

}
