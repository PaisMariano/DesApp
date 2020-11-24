package unq.edu.tpi.desapp.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import unq.edu.tpi.desapp.exceptions.LocationNotFoundException;
import unq.edu.tpi.desapp.model.Location;
import unq.edu.tpi.desapp.repositories.LocationRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class LocationService {

    @Autowired
    private LocationRepository locationRepository;

    @Transactional
    public Location save(Location model) {
        return this.locationRepository.save(model);
    }

    public Location findByID(Integer id) throws Exception{
        return this.locationRepository.findById(id)
                .orElseThrow(() -> LocationNotFoundException.createWith(id.toString()));
    }

    public List<Location> findAll() {
        return this.locationRepository.findAll();
    }

    public Location findByName(String name) {
        List<Location> locations = findAll()
                .stream()
                .filter(elem -> elem.getName().equalsIgnoreCase(name))
                .collect(Collectors.toList());

        if (locations.size() == 0) {
            return null;
        }
        return locations.get(0);
    }
}
