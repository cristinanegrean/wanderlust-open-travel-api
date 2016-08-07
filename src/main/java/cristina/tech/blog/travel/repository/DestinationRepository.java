package cristina.tech.blog.travel.repository;


import cristina.tech.blog.travel.model.Destination;
import cristina.tech.blog.travel.model.Holiday;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.List;
import java.util.Optional;

@RepositoryRestResource(collectionResourceRel = "destinations", path = "destinations")
public interface DestinationRepository extends PagingAndSortingRepository<Destination, Integer> {

    List<Destination> findByCountry(@Param("country") String country);

    Optional<Holiday> findByName(@Param("name") String name);
}
