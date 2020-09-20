package unq.edu.tpi.desapp.repositories;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import unq.edu.tpi.desapp.model.Donation;

import java.util.List;
import java.util.Optional;

@Configuration
@Repository
public interface DonationRepository extends CrudRepository<Donation, Integer> {
    Optional<Donation> findById(Integer id);

    List<Donation> findAll();

}


