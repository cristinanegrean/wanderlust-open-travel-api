package cristina.tech.blog.travel.validation;

import org.springframework.beans.BeanWrapper;
import org.springframework.beans.PropertyAccessorFactory;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class CountryPostalCodeValidator implements ConstraintValidator<CountryPostalCode, Object> {
    private String countryAttribute;
    private String postalCodeAttribute;

    @Override
    public void initialize(final CountryPostalCode constraintAnnotation) {
        this.countryAttribute = constraintAnnotation.country();
        this.postalCodeAttribute = constraintAnnotation.postalCode();
    }

    @Override
    public boolean isValid(final Object value, final ConstraintValidatorContext context) {
        try {
            BeanWrapper wrapper = PropertyAccessorFactory.forBeanPropertyAccess(value);

            String country = (String) wrapper.getPropertyValue(countryAttribute);
            String postalCode = (String) wrapper.getPropertyValue(postalCodeAttribute);

            return PostalCodeFormat.validate(country, postalCode);
        } catch (Exception ignore) {
        }

        return false;
    }
}
