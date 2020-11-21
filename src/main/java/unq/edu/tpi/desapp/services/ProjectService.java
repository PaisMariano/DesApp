package unq.edu.tpi.desapp.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import unq.edu.tpi.desapp.exceptions.*;
import unq.edu.tpi.desapp.model.*;
import unq.edu.tpi.desapp.model.exceptions.*;
import unq.edu.tpi.desapp.repositories.ProjectRepository;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

import static java.util.Comparator.comparing;

@Service
public class ProjectService {

    @Autowired
    private ProjectRepository projectRepository;
    @Autowired
    private LocationService locationService;
    @Autowired
    private ProjectStateService projectStateService;
    @Autowired
    private UserService userService;
    @Autowired
    private DonationService donationService;
    @Autowired
    private ArsatService arsatService;
    @Autowired
    private EmailService emailService;

    @Transactional
    public Project save(Project project) {
        return projectRepository.save(project);
    }

    public Project findByID(Integer id) throws ProjectNotFoundException {
        Project newProject = null;
        try {
            newProject = projectRepository.findById(id).get();
        } catch (NoSuchElementException ex) {
            throw ProjectNotFoundException.createWith(id.toString());
        }
        return newProject;
    }

    public List<Project> findAllProjects() {
        return projectRepository.findAll();
    }

    public List<Location> findAllLocations() { return locationService.findAll(); }

    public Collection<User> findDonorsByProjectID(Integer id) throws ProjectNotFoundException {
        return findByID(id).getUsers();
    }

    public List<Project> findAllProjectsWithIndexes(Integer from, Integer to) throws BadRequestException {
        List<Project> projects = null;
        try {
            projects = projectRepository.findAll()
                    .subList(from, to);
        } catch (IndexOutOfBoundsException | IllegalArgumentException ex){
            throw BadRequestException.createWith("Incorrect params given.");
        }
        return projects;
    }

    public Project createProject(Project project) throws ElementAlreadyExists, BadRequestException {
        Location newLocation = locationService.findByName(project.getLocation().getName());
        if (newLocation != null) {
            throw new ElementAlreadyExists("Location already exists.");
        }
        //Project State Planned() - id 1
        ProjectState projectState = projectStateService.findByID(1);

        Project newProject = null;
        try {
            newProject = new Project(project.getName(),
                    project.getFactor(),
                    project.getMinClosePercentage(),
                    project.getStartDate(),
                    project.getEndDate(),
                    project.getLocation(),
                    projectState);
        } catch (NullPointerException ex) {
            throw BadRequestException.createWith("JSON bad request or missing field.");
        } catch (EndDateMustBeAfterStartDate | InvalidFactor | InvalidMinClosePercentage ex) {
            throw BadRequestException.createWith(ex.getMessage());
        }
        save(newProject);
        return newProject;
    }

    public void updateProject(Integer projectId, Project project) throws ProjectNotFoundException, BadRequestException {
        Project newProject = findByID(projectId);

        try {
            newProject.setName(project.getName());
            newProject.setFactorWithException(project.getFactor());
            newProject.setMinClosePercentageWithException(project.getMinClosePercentage());
            newProject.setStartDate(project.getStartDate());
            newProject.setEndDateWithException(project.getEndDate());
            save(newProject);
        } catch (NullPointerException ex) {
            throw BadRequestException.createWith("JSON bad request or missing field.");
        } catch (EndDateMustBeAfterStartDate | InvalidFactor | InvalidMinClosePercentage ex) {
            throw BadRequestException.createWith(ex.getMessage());
        }
    }

    public void endProject(Integer projectId, Locale locale)
            throws ProjectNotFoundException, ProjectAlreadyConnectedException, FailedEmailException {
        Project newProject = findByID(projectId);
        if (newProject.getProjectState().getState() != "Conectado") {
            newProject.completeProject();
            save(newProject);
            emailService.sendEndingProjectEmail(projectId, locale);
        } else {
            throw new ProjectAlreadyConnectedException("Project already connected");
        }
    }

    public Collection<User> getDonnorsByProjectId(Integer id) throws ProjectNotFoundException {
        return findByID(id).getUsers();
    }
    @Transactional
    public List<String> dailyLeastTenDonatedLocations() {
        List<Project> projects = this.findAllProjects();

        List<Donation> lastDonations = new ArrayList<>();

        projects.stream().forEach(elem -> {
            if (elem.getDonations().size() > 0) {
                lastDonations.add(getLastDonation(elem));
            }
        });

        lastDonations.stream()
                .sorted(comparing(Donation::getDate))
                .limit(10)
                .collect(Collectors.toList());

        List<Location> lastLocations = lastDonations.stream()
                .map(elem -> elem.getProject().getLocation())
                .collect(Collectors.toList());

        return lastLocations.stream()
                .map(elem -> elem.getName() + " - " +  elem.getProvince())
                .collect(Collectors.toList());
    }

    private Donation getLastDonation(Project project) {
        return project.getDonations()
                .stream()
                .sorted(comparing(Donation::getDate).reversed())
                .collect(Collectors.toList())
                .get(0);
    }

    public void updateProjectService(Integer projectId, Integer projectStateId) throws ProjectNotFoundException {
        Project newProject = findByID(projectId);
        ProjectState newProjectState = projectStateService.findByID(projectStateId);
        newProject.setProjectState(newProjectState);
        save(newProject);
    }

    public void updateLocation(Integer locationId, Location location) throws BadRequestException {
        Location newLocation = locationService.findByID(locationId);
        try {
            newLocation.setPopulationWithException(location.getPopulation());
            newLocation.setProvince(location.getProvince());
            locationService.save(newLocation);
        } catch (NullPointerException ex) {
            throw BadRequestException.createWith("JSON bad request or missing field.");
        } catch (IntegerMustBePositive ex) {
            throw BadRequestException.createWith(ex.getMessage());
        }
    }

    public List<ArsatLocation> getAllArsatLocations() {
        return arsatService.getLocations();
    }

    @Transactional
    public void createDonation(Integer projectId, Integer userId, Donation donation) throws ProjectNotFoundException, UserNotFoundException, BadRequestException {
        User user = userService.findByID(userId);
        Project project = findByID(projectId);
        Donation newDonation = null;
        try {
            newDonation = new Donation(donation.getAmount(), donation.getComment(), LocalDate.now(), user.getNickname());
            donationService.save(newDonation);
            project.donate(newDonation, user);

        } catch (NullPointerException ex) {
            throw BadRequestException.createWith("JSON bad request or missing field.");
        } catch (IntegerMustBePositive | EndDateMustBeAfterStartDate | InvalidMinClosePercentage | InvalidFactor ex) {
            throw BadRequestException.createWith(ex.getMessage());
        }
        save(project);
    }
    @Transactional
    public List<String> dailyTopTenDonations() {
        List<Donation> donations = donationService.findAll()
                .stream()
                .filter(elem -> elem.getDate().equals(LocalDate.now()))
                .collect(Collectors.toList());

        List<Donation> donationsTop10 = donations.stream()
                .sorted(comparing(Donation::getAmount).reversed())
                .limit(10)
                .collect(Collectors.toList());

        return donationsTop10.stream()
                .map(elem -> elem.getUser().getNickname() + " - $ " +  elem.getAmount())
                .collect(Collectors.toList());
    }
}
