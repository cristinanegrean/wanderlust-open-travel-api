package cristina.tech.blog.travel.repository;

import cristina.tech.blog.travel.model.Holiday;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.List;

@RepositoryRestResource(collectionResourceRel = "holidays", path = "holidays")
public interface HolidayRepository extends PagingAndSortingRepository<Holiday, Integer> {

    @Query("select h from Holiday h where h.destination.country = :country")
    List<Holiday> findByDestinationCountry(@Param("country") String country);
}
