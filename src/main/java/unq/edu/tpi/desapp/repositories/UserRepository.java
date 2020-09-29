package unq.edu.tpi.desapp.repositories;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import unq.edu.tpi.desapp.model.User;

import java.util.List;
import java.util.Optional;

@Configuration
@Repository
public interface UserRepository extends CrudRepository<User, Integer> {

    Optional<User> findById(Integer id);

    List<User> findAll();

}

