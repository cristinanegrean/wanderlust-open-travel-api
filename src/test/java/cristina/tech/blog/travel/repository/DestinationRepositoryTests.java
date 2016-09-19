package cristina.tech.blog.travel.repository;


import cristina.tech.blog.travel.model.Destination;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;


/**
 * To test the JPA slice of Wanderlust application (Hibernate + Spring Data), I will use the @DataJpaTest annotation.
 * <ul>>A @DataJpaTest will:
 * <li>Configure an in-memory database.</li>
 * <li>Auto-configure Hibernate, Spring Data and the DataSource.</li>
 * <li>Perform an @EntityScan.</li>
 * <li></li>Turn on SQL logging</li>
 * </ul>
 */
@RunWith(SpringRunner.class)
@DataJpaTest
public class DestinationRepositoryTests {
    @Autowired
    private TestEntityManager entityManager; // alternative to JPA EntityManager designed for tests

    @Autowired
    private DestinationRepository repository;

    private static final String   TST_REPO_DEST_NAME        = "Kotor";
    private static final String   TST_REPO_DEST_COUNTRY     = "ME";
    private static final String   TST_REPO_DEST_DESCRIPTION = "Kotor is often called a ‘mini Dubrovnik’, but that hardly does it justice";
    private static final String[] TST_REPO_DEST_FACTS       = {
            "Kotor features as Lonelyplanet #1 City to travel to in 2016: https://www.lonelyplanet.com/best-in-travel/cities/1",
            "It’s a 1200m hike up the town fortifications’ crumbling steps to get to the lookout point on St John’s Hill."
    };

    @Test
    public void saveAndFind() {
        Destination destination = new Destination(
                TST_REPO_DEST_NAME, TST_REPO_DEST_COUNTRY, Arrays.asList(TST_REPO_DEST_FACTS), TST_REPO_DEST_DESCRIPTION);
        entityManager.persist(destination);

        assertThat(repository.findByCountry(TST_REPO_DEST_COUNTRY).size()).isEqualTo(1);

        Optional<Destination> destinationOptional = repository.findByName(TST_REPO_DEST_NAME);
        assertThat(destinationOptional.isPresent()).isEqualTo(true);

        Destination foundDestination = destinationOptional.get();
        assertThat(foundDestination.getCountry()).isEqualTo(TST_REPO_DEST_COUNTRY);
        assertThat(foundDestination.getDescription()).isEqualTo(TST_REPO_DEST_DESCRIPTION);
        assertThat(foundDestination.getFacts().size()).isEqualTo(2);
    }
}
