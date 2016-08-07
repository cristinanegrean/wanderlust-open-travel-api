package cristina.tech.blog.travel.repository;


import cristina.tech.blog.travel.model.Agent;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.Optional;

@RepositoryRestResource(collectionResourceRel = "agents", path = "agents")
public interface AgentRepository extends PagingAndSortingRepository<Agent, Integer> {

    Optional<Agent> findByName(@Param("name") String name);
}
