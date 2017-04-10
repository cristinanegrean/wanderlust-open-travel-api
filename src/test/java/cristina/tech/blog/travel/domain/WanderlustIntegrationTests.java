package cristina.tech.blog.travel.domain;


import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.hateoas.Resource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.http.HttpMethod.GET;
import static org.springframework.http.HttpMethod.PATCH;
import static org.springframework.http.HttpMethod.PUT;


@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class WanderlustIntegrationTests {
    private static final String KOTOR_FACT        =
            "At 1300 metres deep, the Grand Canyon of Tara River is actually the deepest canyon in Europe and second largest in the world after the Colorado canyon in the USA.";
    private static final String KOTOR_DESCRIPTION = "The gem of Montenegro";

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    public void destinations() {
        Destination destinationKotor = new Destination("Kotor", "ME");

        // POST, create destination
        URI uri = restTemplate.postForLocation("/destinations", destinationKotor);

        // setup API response type to be a Destination HTTP Resource
        ParameterizedTypeReference<Resource<Destination>> responseType = new ParameterizedTypeReference<Resource<Destination>>() {
        };

        // GET by URI, id as URI Path Param, i.e.: http://localhost:54294/destinations/1, assert Response HTTP code 200: OK
        ResponseEntity<Resource<Destination>> getDestinationByURI = restTemplate.exchange(uri.toString(), GET, null, responseType);
        assertThat(destinationKotor.getCountry()).isEqualTo(getDestinationByURI.getBody().getContent().getCountry());
        assertThat(HttpStatus.OK.value()).isEqualTo(getDestinationByURI.getStatusCode().value());

        // PUT, replace destination, assert assert Response HTTP code 200: OK
        List<String> facts = new ArrayList<>(1);
        facts.add(KOTOR_FACT);
        destinationKotor.setFacts(facts);
        ResponseEntity<Resource<Destination>> putDestination = restTemplate.exchange(uri.toString(), PUT, new HttpEntity<>(destinationKotor), responseType);
        assertThat(destinationKotor.getFacts()).isEqualTo(putDestination.getBody().getContent().getFacts());
        assertThat(getDestinationByURI.getStatusCodeValue()).isEqualTo(HttpStatus.OK.value());

        // PATCH, update destination, does not work with exchange method, see: https://jira.spring.io/browse/SPR-15347
        destinationKotor.setDescription(KOTOR_DESCRIPTION);
        restTemplate.postForLocation(UriComponentsBuilder.fromUri(uri).queryParam("_method", PATCH.name()).build().toString(), destinationKotor);

        // GET by name, filter on destination name, assert return HTTP code 200
        ResponseEntity<Resource<Destination>> getDestinationByName =
                restTemplate.exchange(UriComponentsBuilder.fromPath("/destinations/search/findByName").queryParam("name", "Kotor").build().toString(), GET, null, responseType);
        assertThat(destinationKotor.getDescription()).isEqualTo(getDestinationByName.getBody().getContent().getDescription());
        assertThat(getDestinationByName.getStatusCode().value()).isEqualTo(HttpStatus.OK.value());

        // DELETE
        restTemplate.delete(uri.toString());

        // GET by country, assert destination does not exist anymore
        ResponseEntity<Resource<Destination>> getDestinationByCountry =
                restTemplate.exchange(UriComponentsBuilder.fromPath("/destinations/search/findByCountry").queryParam("country", "ME").build().toString(), GET, null, responseType);
        assertThat(getDestinationByCountry.getBody().getContent().getId()).isEqualTo(null);
    }
}
