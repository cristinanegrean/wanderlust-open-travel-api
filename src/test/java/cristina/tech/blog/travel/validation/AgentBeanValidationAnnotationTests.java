package cristina.tech.blog.travel.validation;


import cristina.tech.blog.travel.domain.Agent;
import cristina.tech.blog.travel.domain.ContactInfo;
import org.junit.BeforeClass;
import org.junit.Test;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.util.HashSet;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

public class AgentBeanValidationAnnotationTests {

    private static Validator validator;

    @BeforeClass
    public static void setUp() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    public void beanValidationAnnotationsAreChecked() {
        // no agent data set
        Agent agent = new Agent();

        Set<ConstraintViolation<Agent>> constraintViolations = validator.validate(agent);

        // assert name and country validation failed
        assertThat(constraintViolations).hasSize(2);
        Set<String> constraintViolationMessages = new HashSet<>(constraintViolations.size());
        constraintViolations.forEach(acv -> constraintViolationMessages.add(acv.getMessage()));
        assertThat(constraintViolationMessages).contains(
                "Travel agent name cannot be empty!", "Travel agent contact info is mandatory");

        agent.setName("Shoestring");
        ContactInfo contactInfo = new ContactInfo("info@shoestring.nl", "NL", "1114 AA");
        agent.setContactInfo(contactInfo);

        // replay validation
        constraintViolations = validator.validate(agent);

        // assert destination validation passed
        assertThat(constraintViolations).hasSize(0);
    }

}
