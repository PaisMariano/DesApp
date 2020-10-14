package unq.edu.tpi.desapp.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import unq.edu.tpi.desapp.model.Donation;
import unq.edu.tpi.desapp.model.Project;
import unq.edu.tpi.desapp.model.User;
import unq.edu.tpi.desapp.model.exceptions.IntegerMustBePositive;
import unq.edu.tpi.desapp.repositories.DonationRepository;
import unq.edu.tpi.desapp.webservices.exceptions.BadRequestException;
import unq.edu.tpi.desapp.webservices.exceptions.DonationNotFoundException;
import unq.edu.tpi.desapp.webservices.exceptions.ProjectNotFoundException;
import unq.edu.tpi.desapp.webservices.exceptions.UserNotFoundException;

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

    public void createDonation(Integer projectId, Integer userId, Donation donation) throws ProjectNotFoundException, UserNotFoundException, BadRequestException {
        User user = userService.findByID(userId);
        Project project = projectService.findByID(projectId);

        Donation newDonation = null;
        try {
            newDonation = new Donation(
                    donation.getAmount(),
                    donation.getComment(),
                    donation.getDate(),
                    user,
                    project);
            newDonation.calculateUserPoints();
            project.addFunds(donation.getAmount());
        } catch (IntegerMustBePositive ex) {
            throw BadRequestException.createWith(ex.getMessage());
        }
        save(newDonation);
    }
}
