package cristina.tech.blog.travel.validation;


import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Target(TYPE)
@Retention(RUNTIME)
@Constraint(validatedBy = CountryPostalCodeValidator.class)
public @interface CountryPostalCode {

    String message() default "Invalid postal code for country";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    String country();

    String postalCode();
}
