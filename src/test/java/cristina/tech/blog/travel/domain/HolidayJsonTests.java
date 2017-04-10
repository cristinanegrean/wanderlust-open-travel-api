package cristina.tech.blog.travel.domain;


import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.JsonTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.boot.test.json.JsonContent;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.logging.Level;
import java.util.logging.Logger;

import static org.assertj.core.api.Assertions.assertThat;


/**
 * To test the JSON slice of the Wanderlust application, as of testing that JSON serialization and deserialization is working as expected,
 * I will use @JsonTest.
 * <ul>This will:
 * <li>Auto-configure Jackson and/or Gson</li>
 * <li>Add any Module or @JsonComponent beans that you’ve defined</li>
 * <li>Trigger initialization of any JacksonTester or GsonTester fields</li>
 * </ul>
 *
 * @SpringRunner tells JUnit to run using Spring’s testing support. SpringRunner is the new name for SpringJUnit4ClassRunner
 */
@RunWith(SpringRunner.class)
@JsonTest
public class HolidayJsonTests {
    private static final Logger log = Logger.getLogger(HolidayJsonTests.class.getName());

    @Autowired
    private JacksonTester<Holiday> json;

    @Test
    public void testSerialize() throws IOException {
        // Assert serialized holiday object instance against a `.json` file in the same package as the test
        assertThat(json.write(getHoliday(false))).isEqualToJson("holiday_no_destination_link.json");
    }

    @Test
    public void testDeserialize() throws IOException {
        // deserialize
        Holiday holiday = json.readObject("holiday_no_destination_link.json");

        assertThat(holiday).isNotNull();
        assertThat(holiday.getDaysCount()).isEqualTo(15);
        assertThat(holiday.getAccomodationIncluded()).isEqualTo(true);
        assertThat(holiday.getFlightIncluded()).isEqualTo(true);
        assertThat(holiday.getDepartFrom()).isEqualTo("Amsterdam Airport");
        assertThat(holiday.getPackageInfo()).isEqualTo("Group Travel 'On a shoe string'");
        assertThat(holiday.getPrice()).isEqualTo(new BigDecimal(1700));
        assertThat(holiday.getDestination()).isNull();
    }

    @Test
    public void testEmbedded() throws IOException {
        log.setLevel(Level.ALL);
        JsonContent<Holiday> content = json.write(getHoliday(true));
        log.info(String.format("JSON representation of Holiday with embedded Destination: %s", content.getJson()));
        assertThat(content).isEqualToJson("holiday_with_embedded_destination.json");
        assertThat(content).hasJsonPathStringValue("$['destination']['name']");
        assertThat(content).extractingJsonPathStringValue("$['destination']['name']").isEqualTo("Quintessential Japan");
        assertThat(content).hasEmptyJsonPathValue("$['destination']['facts']");
        assertThat(content).extractingJsonPathStringValue("$['destination']['country']").isEqualTo("JP");

        Destination destination = json.readObject("holiday_with_embedded_destination.json").getDestination();
        assertThat(destination).isNotNull();
        assertThat(destination.getName()).isEqualTo("Quintessential Japan");
    }

    private static Holiday getHoliday(boolean destinationEmbedded) {
        Holiday holiday = new Holiday(destinationEmbedded ? getDestination() : null, true, true);
        holiday.setDaysCount(15);
        holiday.setPrice(new BigDecimal(1700));
        holiday.setPackageInfo("Group Travel 'On a shoe string'");
        holiday.setDepartFrom("Amsterdam Airport");

        return holiday;
    }

    private static Destination getDestination() {
        return new Destination("Quintessential Japan", "JP");
    }
}
