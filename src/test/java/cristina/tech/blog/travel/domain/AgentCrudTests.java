package cristina.tech.blog.travel.domain;


import cristina.tech.blog.travel.AgentRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;


/**
 * <p>
 * To test the JPA slice of Wanderlust application (Hibernate + Spring Data), I will use the @DataJpaTest annotation.
 * Note Cobertura or Jacoco - as a matter of fact, don't instrument interfaces.
 * IntelliJ IDEA Analyze Code Coverage either. {@link cristina.tech.blog.travel.AgentRepository} classes are only
 * instantiated by Spring at runtime. However slice testing CRUD operations is still good engineering practice.
 * </p>
 * <ul>>A @DataJpaTest will:
 * <li>Configure an in-memory database.</li>
 * <li>Auto-configure Hibernate, Spring Data and the DataSource.</li>
 * <li>Perform an @EntityScan.</li>
 * <li></li>Turn on SQL logging</li>
 * </ul>
 */
@RunWith(SpringRunner.class)
@DataJpaTest
public class AgentCrudTests {
    @Autowired
    private TestEntityManager entityManager; // alternative to JPA EntityManager designed for tests

    @Autowired
    private AgentRepository agentRepository;

    @Test
    public void performCrud() {
        // before being able to create a holiday, we need a link to a persisted destination first
        Destination destination = new Destination("Cluj-Napoca", "RO");
        destination.setFacts(Arrays.asList("Student capital of Transylvania, great art galleries and theathers"));

        // insert destination
        entityManager.persist(destination);

        // before being able to create an agent, we need a link to a persisted holiday first
        Holiday holiday = new Holiday(null, true, true);
        holiday.setDaysCount(4);
        holiday.setPrice(new BigDecimal(150));
        holiday.setStartOn(LocalDateTime.now().plusDays(1));
        holiday.setPackageInfo("Group Travel 'On a shoe string'");
        holiday.setDepartFrom("Eindhoven Airport");
        holiday.setDestination(destination);

        // insert holiday
        entityManager.persist(holiday);

        // create agent
        Agent agent = new Agent("Shoestring");
        ContactInfo contactInfo = new ContactInfo("info@shoestring.nl", "NL", "1114 AA");
        contactInfo.setPhone("+31 617181119");
        agent.setContactInfo(contactInfo);
        Set<Holiday> holidays = new HashSet<>(1);
        holidays.add(holiday);
        agent.setHolidays(holidays);

        // insert agent
        entityManager.persist(agent);

        // find agent
        assertThat(agentRepository.findByName("Shoestring").isPresent()).isEqualTo(true);
        assertThat(agentRepository.count()).isEqualTo(1);

        // assert that auditing fields as created and modified date fields are set automatically
        assertThat(agent.createdAt).isNotNull();
        assertThat(agent.modifiedAt).isNotNull();

        // upon persist create, assert date fields are the same
        assertThat(agent.createdAt).isSameAs(agent.modifiedAt);
    }

    @Test(expected = javax.validation.ConstraintViolationException.class)
    public void saveInvalid() {
        // invalid agent
        Agent agent = new Agent();

        // HibernateValidator kicks-off and save fails with ConstraintViolationException
        agentRepository.save(agent);
    }
}
