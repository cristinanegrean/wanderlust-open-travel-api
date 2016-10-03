package cristina.tech.blog.travel.domain;


import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.hateoas.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;
import java.net.URI;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.http.HttpMethod.GET;


@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment= SpringBootTest.WebEnvironment.RANDOM_PORT)
public class WanderlustIntegrationTests {

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    public void destinations() throws IOException {
        Destination destinationKotor = new Destination("Kotor", "ME");

        // POST
        URI uri = restTemplate.postForLocation("/destinations", destinationKotor);

        // GET
        ParameterizedTypeReference<Resource<Destination>> responseType = new ParameterizedTypeReference<Resource<Destination>>() {};
        ResponseEntity<Resource<Destination>> responseEntity = restTemplate.exchange(uri.toString(), GET, null, responseType);
        assertThat(destinationKotor.getCountry()).isEqualTo(responseEntity.getBody().getContent().getCountry());

        // DELETE
        restTemplate.delete(uri.toString());
    }

}
