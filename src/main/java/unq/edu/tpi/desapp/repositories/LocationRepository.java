package unq.edu.tpi.desapp.repositories;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import unq.edu.tpi.desapp.model.Location;

import java.util.List;
import java.util.Optional;

@Configuration
@Repository
public interface LocationRepository extends CrudRepository<Location, Integer> {

    Optional<Location> findById(Integer id);

    List<Location> findAll();

}