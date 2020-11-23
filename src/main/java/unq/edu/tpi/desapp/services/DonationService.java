package unq.edu.tpi.desapp.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import unq.edu.tpi.desapp.model.Donation;
import unq.edu.tpi.desapp.repositories.DonationRepository;
import unq.edu.tpi.desapp.exceptions.DonationNotFoundException;

import java.util.List;

@Service
public class DonationService {

    @Autowired
    private DonationRepository donationRepository;

    @Transactional
    public Donation save(Donation model) {
        return this.donationRepository.save(model);
    }

    public Donation findByID(Integer id) throws Exception {
        return this.donationRepository.findById(id)
                .orElseThrow(() -> DonationNotFoundException.createWith(id.toString()));
    }

    public List<Donation> findAll() {
        return this.donationRepository.findAll();
    }

}
