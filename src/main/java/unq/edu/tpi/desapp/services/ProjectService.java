package unq.edu.tpi.desapp.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import unq.edu.tpi.desapp.model.*;
import unq.edu.tpi.desapp.model.exceptions.*;
import unq.edu.tpi.desapp.repositories.ProjectRepository;
import unq.edu.tpi.desapp.webservices.exceptions.BadRequestException;
import unq.edu.tpi.desapp.webservices.exceptions.ElementAlreadyExists;
import unq.edu.tpi.desapp.webservices.exceptions.ProjectNotFoundException;

import java.util.Collection;
import java.util.List;
import java.util.NoSuchElementException;

@Service
public class ProjectService {

    @Autowired
    private ProjectRepository projectRepository;
    @Autowired
    private LocationService locationService;
    @Autowired
    private ProjectStateService projectStateService;

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

    public Project createProject(Project project) throws ElementAlreadyExists, BadRequestException {
        verifyProjectJson(project);

        Location newLocation = locationService.findByName(project.getLocation().getName());
        if (newLocation != null) {
            throw ElementAlreadyExists.createWith();
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
        } catch (EndDateMustBeAfterStartDate | InvalidFactor | InvalidMinClosePercentage ex) {
            throw BadRequestException.createWith(ex.getMessage());
        }
    }

    public Collection<User> getDonnorsByProjectId(Integer id) throws ProjectNotFoundException {
        return findByID(id).getUsers();
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
        } catch (IntegerMustBePositive ex) {
            throw BadRequestException.createWith(ex.getMessage());
        }
    }

    private void verifyProjectJson(Project project) throws BadRequestException {
        try{
            project.getFactor();
            project.getMinClosePercentage();
            project.getName();
            project.getStartDate();
            project.getEndDate();
        } catch (Exception ex) {
            throw BadRequestException.createWith("Wrong body or no body in request");
        }
    }
}
