package unq.edu.tpi.desapp.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import unq.edu.tpi.desapp.model.Donation;
import unq.edu.tpi.desapp.model.Project;
import unq.edu.tpi.desapp.model.User;
import unq.edu.tpi.desapp.repositories.DonationRepository;

import java.util.List;

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

    public Donation findByID(Integer id) {
        return this.donationRepository.findById(id).get();
    }

    public List<Donation> findAll() {
        return this.donationRepository.findAll();
    }

    public void createDonation(Integer projectId, Integer userId, Donation donation) {
        User user = userService.findByID(userId);
        Project project = projectService.findByID(projectId);

        Donation newDonation = new Donation(
                donation.getAmount(),
                donation.getComment(),
                donation.getDate(),
                user,
                project);
        save(newDonation);
    }
}
