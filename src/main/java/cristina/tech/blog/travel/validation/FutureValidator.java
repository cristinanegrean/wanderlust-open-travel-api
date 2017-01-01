package cristina.tech.blog.travel.validation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.time.LocalDateTime;
import java.time.temporal.Temporal;

/**
 * At this time javax.validation.constraints.Future and javax.validation.constraints.Past are not supported in Hibernate
 * Validator in combination with Java 8 Date Time API. Work fine for Joda DateTime, as for JSR 310 and JSR 303/349,
 * introducing a custom implementation of the Future validator is required.
 * https://hibernate.atlassian.net/projects/BVAL/issues/BVAL-496?filter=allopenissues
 */
public class FutureValidator implements ConstraintValidator<Future, Temporal> {
    @Override
    public void initialize(Future constraintAnnotation) {
    }

    @Override
    public boolean isValid(Temporal value, ConstraintValidatorContext context) {
        if (value == null) {
            return true;
        }
        LocalDateTime ld = LocalDateTime.from(value);
        return ld.isAfter(LocalDateTime.now());
    }
}
