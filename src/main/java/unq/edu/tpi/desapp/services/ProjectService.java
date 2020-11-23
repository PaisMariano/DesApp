package unq.edu.tpi.desapp.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import unq.edu.tpi.desapp.aspects.ExceptionAspect;
import unq.edu.tpi.desapp.exceptions.*;
import unq.edu.tpi.desapp.model.*;
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
        return projectRepository.findById(id)
                .orElseThrow(() -> ProjectNotFoundException.createWith(id.toString()));
    }

    public List<Project> findAllProjects() {
        return projectRepository.findAll();
    }

    public List<Location> findAllLocations() { return locationService.findAll(); }

    public Collection<User> findDonorsByProjectID(Integer id) throws ProjectNotFoundException {
        return findByID(id).getUsers();
    }

    @ExceptionAspect
    public List<Project> findAllProjectsWithIndexes(Integer from, Integer to) throws Exception {
        List<Project> projects = null;
        projects = projectRepository.findAll()
                .subList(from, to);

        return projects;
    }

    @ExceptionAspect
    public Project createProject(Project project) throws Exception {
        Location newLocation = locationService.findByName(project.getLocation().getName());
        if (newLocation != null) {
            throw new ElementAlreadyExists("Location already exists.");
        }
        //Project State Planned() - id 1
        ProjectState projectState = projectStateService.findByID(1);

        Project newProject = null;

        newProject = new Project(project.getName(),
                project.getFactor(),
                project.getMinClosePercentage(),
                project.getStartDate(),
                project.getEndDate(),
                project.getLocation(),
                projectState);

        save(newProject);
        return newProject;
    }

    @ExceptionAspect
    public void updateProject(Integer projectId, Project project) throws Exception {
        Project newProject = findByID(projectId);

        newProject.setName(project.getName());
        newProject.setFactorWithException(project.getFactor());
        newProject.setMinClosePercentageWithException(project.getMinClosePercentage());
        newProject.setStartDate(project.getStartDate());
        newProject.setEndDateWithException(project.getEndDate());
        save(newProject);
    }

    public void endProject(Integer projectId, Locale locale)
            throws Exception {
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

    @ExceptionAspect
    public Location updateLocation(Integer locationId, Location location) throws Exception {
        Location newLocation = locationService.findByID(locationId);

        newLocation.setPopulationWithException(location.getPopulation());
        newLocation.setProvince(location.getProvince());
        locationService.save(newLocation);

        return newLocation;
    }

    public List<ArsatLocation> getAllArsatLocations() {
        return arsatService.getLocations();
    }

    @ExceptionAspect
    @Transactional
    public Donation createDonation(Integer projectId, Integer userId, Donation donation) throws Exception {
        User user = userService.findByID(userId);
        Project project = findByID(projectId);
        Donation newDonation = null;

        newDonation = new Donation(donation.getAmount(), donation.getComment(), LocalDate.now(), user.getNickname());
        donationService.save(newDonation);
        project.donate(newDonation, user);

        save(project);
        return newDonation;
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
