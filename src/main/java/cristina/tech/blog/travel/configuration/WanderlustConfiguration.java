package cristina.tech.blog.travel.configuration;


import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.rest.core.event.ValidatingRepositoryEventListener;
import org.springframework.data.rest.webmvc.config.RepositoryRestConfigurerAdapter;
import org.springframework.validation.Validator;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;

@Configuration
public class WanderlustConfiguration extends RepositoryRestConfigurerAdapter {

    /**
     * The basic configuration above will trigger Bean Validation to initialize using its default bootstrap mechanism. A JSR-303/JSR-349 provider, such as Hibernate Validator,
     * is expected to be present in the classpath and will be detected automatically.
     */
    @Bean
    @Primary
    Validator validator() {
        return new LocalValidatorFactoryBean();
    }

    /**
     * Overrides default behavior by adding validators manually.
     * @param validatingListener The {@link org.springframework.context.ApplicationListener} responsible for invoking Validator instances.
     */
    @Override
    public void configureValidatingRepositoryEventListener(ValidatingRepositoryEventListener validatingListener) {
        Validator validator = validator();
        validatingListener.addValidator("beforeCreate", validator);
        validatingListener.addValidator("beforeSave", validator);
    }

    @Override
    public void configureJacksonObjectMapper(ObjectMapper objectMapper) {
        super.configureJacksonObjectMapper(objectMapper);
    }
}
