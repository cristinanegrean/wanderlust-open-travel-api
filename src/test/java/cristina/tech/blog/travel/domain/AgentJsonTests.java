package cristina.tech.blog.travel.domain;


import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.JsonTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.boot.test.json.JsonContent;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

import static java.util.stream.Collectors.toList;
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
public class AgentJsonTests {
    private static final Logger log = Logger.getLogger(HolidayJsonTests.class.getName());

    @Autowired
    private JacksonTester<Agent> json;

    @Test
    public void testSerialize() throws IOException {
        // Assert serialized agent object instance against a `.json` file in the same package as the test
        assertThat(json.write(getAgent(false))).isEqualToJson("agent_no_holidays_link.json");
    }

    @Test
    public void testDeserialize() throws IOException {
        // deserialize
        Agent agent = json.readObject("agent_no_holidays_link.json");

        assertThat(agent).isNotNull();
        assertThat(agent.getName()).isEqualTo("WIZZ Tours");
        assertThat(agent.getContactInfo()).isNotNull();

        ContactInfo contactInfo = agent.getContactInfo();
        assertThat(contactInfo.getEmail()).isEqualTo("info@wizztours.com");
        assertThat(contactInfo.getCountry()).isEqualTo("GB");
        assertThat(contactInfo.getCity()).isEqualTo("London");
        assertThat(contactInfo.getPostalCode()).isEqualTo("AB10 1AA");
        assertThat(contactInfo.getWebsite()).isEqualTo("https://wizztours.com");
        assertThat(contactInfo.getPhone()).isEqualTo("+44 333 155 4997");
        assertThat(contactInfo.getFax()).isEqualTo("+44 333 155 4991");
    }

    @Test
    public void testEmbedded() throws IOException {
        log.setLevel(Level.ALL);
        JsonContent<Agent> content = json.write(getAgent(true));
        log.info(String.format("JSON representation of Agent with embedded collection of Holiday resources: %s", content));
        assertThat(content).isEqualToJson("agent_with_embedded_holidays.json");
        assertThat(content).extractingJsonPathArrayValue("$['holidays']").hasSameSizeAs(getHolidays());
        assertThat(content).extractingJsonPathStringValue(("$['holidays'][1]['destination']['country']")).isIn(getHolidays().stream().map(h -> h.getDestination().getCountry()).collect(toList()));
    }

    private static Agent getAgent(boolean holidaysEmbedded) {
        Agent agent = new Agent("WIZZ Tours");

        ContactInfo contactInfo = new ContactInfo("info@wizztours.com", "GB", "AB10 1AA");
        contactInfo.setCity("London");
        contactInfo.setPhone("+44 333 155 4997");
        contactInfo.setFax("+44 333 155 4991");
        contactInfo.setWebsite("https://wizztours.com");
        agent.setContactInfo(contactInfo);
        if (holidaysEmbedded) {
            agent.setHolidays(getHolidays());
        }

        return agent;
    }

    private static Set<Holiday> getHolidays() {
        Set<Holiday> wizzHolidays = new HashSet<>(2);
        Holiday transylvanianNatureFever = new Holiday(new Destination("Hiking and wild brown bear-spotting in the Carpathian Mountains", "RO"), true, true);
        Holiday cityTripGetaway = new Holiday(new Destination("Visit Aarhus: 2017 european capital of culture", "DK"), true, false);
        wizzHolidays.add(transylvanianNatureFever);
        wizzHolidays.add(cityTripGetaway);

        return wizzHolidays;
    }
}
