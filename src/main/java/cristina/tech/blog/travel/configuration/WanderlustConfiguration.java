package cristina.tech.blog.travel.configuration;


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
     * Overridding default behavior by adding HibernateValidator instance manually.
     *
     * @see org.springframework.data.rest.webmvc.config.RepositoryRestConfigurer#configureValidatingRepositoryEventListener(
     * org.springframework.data.rest.core.event.ValidatingRepositoryEventListener)
     * @param validatingListener The {@link org.springframework.context.ApplicationListener} responsible for invoking the HibernateValidator instance after the correct event.
     * There are eight different events that the REST exporter emits throughout the process of working with an entity. Those are: beforeCreate, afterCreate, beforeSave, afterSave,
     *                           beforeLinkSave, afterLinkSave, beforeDelete, afterDelete
     */
    @Override
    public void configureValidatingRepositoryEventListener(ValidatingRepositoryEventListener validatingListener) {
        Validator validator = validator();
        validatingListener.addValidator("beforeCreate", validator);
        validatingListener.addValidator("beforeSave", validator);
    }
}
