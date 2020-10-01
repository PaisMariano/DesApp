package unq.edu.tpi.desapp.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import unq.edu.tpi.desapp.model.Location;
import unq.edu.tpi.desapp.repositories.LocationRepository;

import java.util.List;

@Service
public class LocationService {

    @Autowired
    private LocationRepository locationRepository;

    @Transactional
    public Location save(Location model) {
        return this.locationRepository.save(model);
    }

    public Location findByID(Integer id) {
        return this.locationRepository.findById(id).get();
    }

    public List<Location> findAll() {
        return this.locationRepository.findAll();
    }
}
