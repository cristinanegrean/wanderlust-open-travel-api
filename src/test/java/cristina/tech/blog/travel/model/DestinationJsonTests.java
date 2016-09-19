package cristina.tech.blog.travel.model;


import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.JsonTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;

import static org.assertj.core.api.Assertions.assertThat;


/**
 * To test the JSON slice of the Wanderlust application, as of testing that JSON serialization and deserialization is working as expected,
 * I will use @JsonTest.
 * <ul>This will:
 *  <li>Auto-configure Jackson and/or Gson</li>
 *  <li>Add any Module or @JsonComponent beans that you’ve defined</li>
 *  <li>Trigger initialization of any JacksonTester or GsonTester fields</li>
 * </ul>
 *  @SpringRunner tells JUnit to run using Spring’s testing support. SpringRunner is the new name for SpringJUnit4ClassRunner
 */
@RunWith(SpringRunner.class)
@JsonTest
public class DestinationJsonTests {

    @Autowired
    private JacksonTester<Destination> json;

    private static final String   TST_SER_DEST_NAME        = "Transylvania";
    private static final String   TST_SER_DEST_COUNTRY     = "RO";
    private static final String   TST_SER_DEST_DESCRIPTION = "Mountain thrills and edgy art in Vlad's former home";
    private static final String[] TST_SER_DEST_FACTS       = {
            "Transylvania features as Lonelyplanet #1 Region to travel to in 2016: http://www.lonelyplanet.com/best-in-travel/regions/1"
    };

    private static final String   TST_DESER_DEST_NAME        = "Quintessential Japan";
    private static final String   TST_DESER_DEST_COUNTRY     = "JP";
    private static final String   TST_DESER_DEST_DESCRIPTION = "Life changing experience";
    private static final String[] TST_DESER_DEST_FACTS       = {
            "There are over 5.5 million vending machines in Japan selling everything from umbrellas and cigarettes to canned bread and hot noodles.",
            "It is estimated that more paper is used for manga comics than for toilet paper in Japan. (Surprise: both are sold in vending machines as well.)"
    };

    @Test
    public void testSerialize() throws Exception {

        Destination destination = new Destination(
                TST_SER_DEST_NAME, TST_SER_DEST_COUNTRY, Arrays.asList(TST_SER_DEST_FACTS), TST_SER_DEST_DESCRIPTION);

        // Assert serialized destination object instance against a `.json` file in the same package as the test
        assertThat(json.write(destination)).isEqualToJson("expectedDestinationSerialize.json");

        // Or use JSON path based assertions
        assertThat(json.write(destination)).hasJsonPathStringValue("@.name");
        assertThat(json.write(destination)).hasJsonPathStringValue("@.country");
        assertThat(json.write(destination)).hasJsonPathArrayValue("@.facts");
        assertThat(json.write(destination)).hasJsonPathStringValue("@.description");

        assertThat(json.write(destination)).extractingJsonPathStringValue("@.name").isEqualTo(TST_SER_DEST_NAME);
        assertThat(json.write(destination)).extractingJsonPathStringValue("@.country").isEqualTo(TST_SER_DEST_COUNTRY);
        assertThat(json.write(destination)).extractingJsonPathStringValue("@.description").isEqualTo(TST_SER_DEST_DESCRIPTION);
    }

    @Test
    public void testDeserialize() throws Exception {

       // Assert deserialized '.json' file content leads to non-null destination object
       assertThat(json.readObject("actualDestinationDeserialize.json")).isNotNull();

        // Use JSON path based assertions to assert deserialized content structure
        assertThat(json.readObject("actualDestinationDeserialize.json").getName()).isEqualTo(TST_DESER_DEST_NAME);
        assertThat(json.readObject("actualDestinationDeserialize.json").getCountry()).isEqualTo(TST_DESER_DEST_COUNTRY);
        assertThat(json.readObject("actualDestinationDeserialize.json").getDescription()).isEqualTo(TST_DESER_DEST_DESCRIPTION);
        assertThat(json.readObject("actualDestinationDeserialize.json").getFacts().size()).isEqualTo(2);
    }


}
