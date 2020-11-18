package unq.edu.tpi.desapp.repositories;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import unq.edu.tpi.desapp.model.ProjectState;

import java.util.List;
import java.util.Optional;

@Configuration
@Repository
public interface ProjectStateRepository extends CrudRepository<ProjectState, Integer> {

    Optional<ProjectState> findById(Integer id);

    List<ProjectState> findAll();

}