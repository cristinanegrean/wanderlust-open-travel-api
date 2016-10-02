package cristina.tech.blog.travel.domain;


import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.JsonTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.test.context.junit4.SpringRunner;

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

    @Autowired
    private JacksonTester<Agent> json;

    @Test
    public void testSerialize() throws Exception {
        Agent agent = new Agent("WIZZ Tours");

        ContactInfo contactInfo = new ContactInfo("info@wizztours.com", "GB", "AB10 1AA");
        contactInfo.setCity("London");
        contactInfo.setPhone("+44 333 155 4997");
        contactInfo.setFax("+44 333 155 4991");
        contactInfo.setWebsite("https://wizztours.com");
        agent.setContactInfo(contactInfo);

        // Assert serialized agent object instance against a `.json` file in the same package as the test
        assertThat(json.write(agent)).isEqualToJson("agent_no_holidays_link.json");
    }

    @Test
    public void testDeserialize() throws Exception {
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

}
