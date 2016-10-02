package cristina.tech.blog.travel.domain;


import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.JsonTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.boot.test.json.JsonContent;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;

import static org.assertj.core.api.Assertions.assertThat;


/**
 * To test the JSON slice of the Wanderlust application, as of testing that JSON serialization and deserialization is working as expected,
 * I will use @JsonTest annotation.
 * <ul>This will:
 * <li>Auto-configure Jackson or Gson</li>
 * <li>Add any Module or @JsonComponent beans that you’ve defined</li>
 * <li>Trigger initialization of any JacksonTester or GsonTester fields</li>
 * </ul>
 * <p>
 * {@link SpringRunner} tells JUnit to run using Spring’s testing support. SpringRunner is the new name for SpringJUnit4ClassRunner
 */
@RunWith(SpringRunner.class)
@JsonTest
public class DestinationJsonTests {

    @Autowired
    private JacksonTester<Destination> json;

    private static final String TRANSYLVANIA = "Transylvania";
    private static final String RO = "RO";
    private static final String MOUNTAIN_THRILLS_AND_EDGY_ART_IN_VLAD_S_FORMER_HOME = "Mountain thrills and edgy art in Vlad's former home";
    private static final String TRANSYLVANIA_FACT =
            "Transylvania features as Lonelyplanet #1 Region to travel to in 2016: http://www.lonelyplanet.com/best-in-travel/regions/1";

    private static final String QUINTESSENTIAL_JAPAN = "Quintessential Japan";
    private static final String JP = "JP";
    private static final String LIFE_CHANGING_EXPERIENCE = "Life changing experience";
    private static final String JAPAN_FACT_ONE =
            "There are over 5.5 million vending machines in Japan selling everything from umbrellas and cigarettes to canned bread and hot noodles.";
    private static final String JAPAN_FACT_TWO =
            "It is estimated that more paper is used for manga comics than for toilet paper in Japan. (Surprise: both are sold in vending machines as well.)";

    @Test
    public void testSerialize() throws Exception {
        Destination destination = new Destination(
                TRANSYLVANIA, RO, Arrays.asList(TRANSYLVANIA_FACT), MOUNTAIN_THRILLS_AND_EDGY_ART_IN_VLAD_S_FORMER_HOME);

        // serialize Destination POJO to JSON
        JsonContent<Destination> jsonContent = json.write(destination);

        // Assert against a `.json` file in the same package as the test
        assertThat(jsonContent).isEqualToJson("destination_Transylvania.json");

        // Or use JSON path based assertions
        assertThat(jsonContent).hasJsonPathStringValue("@.name");
        assertThat(jsonContent).hasJsonPathStringValue("@.country");
        assertThat(jsonContent).hasJsonPathArrayValue("@.facts");
        assertThat(jsonContent).hasJsonPathStringValue("@.description");

        assertThat(jsonContent).extractingJsonPathStringValue("@.name").isEqualTo(TRANSYLVANIA);
        assertThat(jsonContent).extractingJsonPathStringValue("@.country").isEqualTo(RO);
        assertThat(jsonContent).extractingJsonPathStringValue("@.description").isEqualTo(MOUNTAIN_THRILLS_AND_EDGY_ART_IN_VLAD_S_FORMER_HOME);
        assertThat(jsonContent).extractingJsonPathArrayValue("@.facts").containsOnly(TRANSYLVANIA_FACT);
    }

    @Test
    public void testDeserialize() throws Exception {
        // deserialize JSON to Destination POJO
        Destination destination = json.readObject("destination_Japan.json");

        assertThat(destination).isNotNull();
        assertThat(destination.getName()).isEqualTo(QUINTESSENTIAL_JAPAN);
        assertThat(destination.getCountry()).isEqualTo(JP);
        assertThat(destination.getDescription()).isEqualTo(LIFE_CHANGING_EXPERIENCE);
        assertThat(destination.getFacts()).hasSize(2);
        assertThat(destination.getFacts()).contains(JAPAN_FACT_ONE, JAPAN_FACT_TWO);
    }

}