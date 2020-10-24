package unq.edu.tpi.desapp.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import unq.edu.tpi.desapp.model.Donation;
import unq.edu.tpi.desapp.repositories.DonationRepository;
import unq.edu.tpi.desapp.webservices.exceptions.DonationNotFoundException;

import java.util.List;
import java.util.NoSuchElementException;

@Service
public class DonationService {

    @Autowired
    private DonationRepository donationRepository;
    @Autowired
    private UserService userService;
    @Autowired
    private ProjectService projectService;

    @Transactional
    public Donation save(Donation model) {
        return this.donationRepository.save(model);
    }

    public Donation findByID(Integer id) throws DonationNotFoundException {
        Donation newDonation = null;
        try{
            newDonation = this.donationRepository.findById(id).get();
        } catch (NoSuchElementException ex) {
            throw DonationNotFoundException.createWith(id.toString());
        }
        return newDonation;
    }

    public List<Donation> findAll() {
        return this.donationRepository.findAll();
    }

}
