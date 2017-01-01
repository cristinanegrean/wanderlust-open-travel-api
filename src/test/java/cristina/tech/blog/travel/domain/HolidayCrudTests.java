package cristina.tech.blog.travel.domain;


import cristina.tech.blog.travel.DestinationRepository;
import cristina.tech.blog.travel.HolidayRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;

import javax.validation.ConstraintViolationException;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Arrays;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * <p>
 * To test the JPA slice of Wanderlust application (Hibernate + Spring Data), I will use the @DataJpaTest annotation.
 * Note Cobertura or Jacoco - as a matter of fact, don't instrument interfaces.
 * IntelliJ IDEA Analyze Code Coverage either. {@link cristina.tech.blog.travel.HolidayRepository} classes are only
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
public class HolidayCrudTests {
    private static final String TRANSYLVANIA_FACT =
            "Transylvania features as Lonelyplanet #1 Region to travel to in 2016: https://www.youtube.com/watch?v=IEzjvhdJLaM";

    @Autowired
    private TestEntityManager entityManager; // alternative to JPA EntityManager designed for tests

    @Autowired
    private HolidayRepository holidayRepository;

    @Autowired
    private DestinationRepository destinationRepository;

    @Test(expected = ConstraintViolationException.class)
    public void saveInvalid() {
        // invalid Holiday
        Holiday holiday = new Holiday();

        // HibernateValidator kicks-off and save fails with ConstraintViolationException
        holidayRepository.save(holiday);
    }

    @Test
    public void performCrud() {
        // before being able to create a holiday, we need a link to a persisted destination first
        Destination destination = new Destination("Transylvania", "RO");
        destination.setFacts(Arrays.asList(TRANSYLVANIA_FACT));

        // insert destination
        entityManager.persist(destination);

        Holiday holiday = new Holiday(null, true, true);
        holiday.setDaysCount(15);
        holiday.setPrice(new BigDecimal(1700));
        holiday.setStartOn(LocalDateTime.now().plusMonths(5));
        holiday.setPackageInfo("Group Travel 'On a shoe string'");
        holiday.setDepartFrom("Amsterdam Airport");
        holiday.setDestination(destination);

        // insert
        entityManager.persist(holiday);

        // find holiday by destination country
        assertThat(holidayRepository.findByDestinationCountry(destination.getCountry()).size()).isEqualTo(1);
        assertThat(holidayRepository.count()).isEqualTo(1);

        // delete old holiday packages, or you could update startsOn date ;)
        holidayRepository.delete(holiday);
        assertThat(holidayRepository.count()).isEqualTo(0);
    }
}
