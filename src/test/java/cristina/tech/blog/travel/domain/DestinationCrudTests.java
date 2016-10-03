package cristina.tech.blog.travel.domain;


import cristina.tech.blog.travel.DestinationRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;


/**
 * <p>
 * To test the JPA slice of Wanderlust application (Hibernate + Spring Data), I will use the @DataJpaTest annotation.
 * Note Cobertura or Jacoco - as a matter of fact, don't instrument interfaces.
 * IntelliJ IDEA Analyze Code Coverage either. {@link DestinationRepository} classes are only instantiated by Spring
 * at runtime. However slice testing CRUD operations is still good engineering practice.
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
public class DestinationCrudTests {
    @Autowired
    private TestEntityManager entityManager; // alternative to JPA EntityManager designed for tests

    @Autowired
    private DestinationRepository destinationRepository;

    private static final String KOTOR = "Kotor";
    private static final String ME = "ME";
    private static final String KOTOR_DESCRIPTION = "Kotor is often called a ‘mini Dubrovnik’, but that hardly does it justice";
    private static final String KOTOR_FACT_ONE = "Kotor features as Lonelyplanet #1 City to travel to in 2016: https://www.lonelyplanet.com/best-in-travel/cities/1";
    private static final String KOTOR_FACT_TWO = "It’s a 1200m hike up the town fortifications’ crumbling steps to get to the lookout point on St John’s Hill.";
    private static final String KOTOR_FACT_THREE = "Kotor is on Cristina's travel list!";
    private static final String KOTOR_DESCRIPTION_UPDATE = "Kotor is a coastal town in Montenegro. It is located in a secluded part of the Gulf of Kotor";

    private static final String[] KOTOR_FACTS = {KOTOR_FACT_ONE, KOTOR_FACT_TWO};

    @Test
    public void performCrud() {
        Destination destination = new Destination(KOTOR, ME, new ArrayList<String>(Arrays.asList(KOTOR_FACTS)), KOTOR_DESCRIPTION);
        // insert
        entityManager.persist(destination);

        // find by country
        assertThat(destinationRepository.findByCountry(ME).size()).isEqualTo(1);

        // find by name
        Optional<Destination> destinationOptional = destinationRepository.findByName(KOTOR);
        assertThat(destinationOptional.isPresent()).isEqualTo(true);

        // count find all
        assertThat(destinationRepository.findAll()).hasSize(1);
        Destination foundDestination = destinationOptional.get();

        // check found destination is same as inserted
        assertThat(foundDestination.getCountry()).isEqualTo(ME);
        assertThat(foundDestination.getDescription()).isEqualTo(KOTOR_DESCRIPTION);
        assertThat(foundDestination.getFacts()).hasSize(2);
        assertThat(foundDestination.getFacts()).contains(KOTOR_FACT_ONE, KOTOR_FACT_TWO);

        // update add additional fact
        foundDestination.setFacts(new ArrayList<String>(Arrays.asList(KOTOR_FACT_ONE, KOTOR_FACT_TWO, KOTOR_FACT_THREE)));
        foundDestination.setDescription(KOTOR_DESCRIPTION_UPDATE);
        destinationRepository.save(foundDestination);

        // reload, find by id
        Destination updatedDestination = destinationRepository.findOne(foundDestination.getId());

        // assert 3rd fact added
        assertThat(updatedDestination.getFacts()).hasSize(3);
        assertThat(updatedDestination.getFacts()).contains(KOTOR_FACT_THREE);
        assertThat(updatedDestination.getDescription()).isEqualTo(KOTOR_DESCRIPTION_UPDATE);
    }

    @Test(expected = javax.validation.ConstraintViolationException.class)
    public void saveInvalid() {
        // invalid destination
        Destination destination = new Destination();

        // HibernateValidator kicks-off and save fails with ConstraintViolationException
        destinationRepository.save(destination);
    }

}
