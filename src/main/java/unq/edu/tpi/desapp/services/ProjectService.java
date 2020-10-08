package unq.edu.tpi.desapp.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import unq.edu.tpi.desapp.model.*;
import unq.edu.tpi.desapp.model.exceptions.*;
import unq.edu.tpi.desapp.repositories.ProjectRepository;

import java.util.Collection;
import java.util.List;

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

    public Project findByID(Integer id) {
        return projectRepository.findById(id).get();
    }

    public List<Project> findAllProjects() {
        return projectRepository.findAll();
    }

    public List<Location> findAllLocations() { return locationService.findAll(); }

    public Collection<User> findDonorsByProjectID(Integer id) {
        return findByID(id).getUsers();
    }

    public Project createProject(Project project) throws ElementAlreadyExists {
        Location newLocation = locationService.findByName(project.getLocation().getName());
        if (newLocation != null) {
            throw new ElementAlreadyExists();
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
        } catch (EndDateMustBeAfterStartDate endDateMustBeAfterStartDate) {
            endDateMustBeAfterStartDate.printStackTrace();
        } catch (InvalidFactor invalidFactor) {
            invalidFactor.printStackTrace();
        } catch (InvalidMinClosePercentage invalidMinClosePercentage) {
            invalidMinClosePercentage.printStackTrace();
        }
        save(newProject);
        return newProject;
    }

    public void updateProject(Integer projectId, Project project) {
        try {
            Project newProject = findByID(projectId);
            newProject.setName(project.getName());
            newProject.setFactor(project.getFactor());
            newProject.setMinClosePercentage(project.getMinClosePercentage());
            newProject.setStartDate(project.getStartDate());
            newProject.setEndDate(project.getEndDate());
            save(newProject);
        } catch (EndDateMustBeAfterStartDate endDateMustBeAfterStartDate) {
            endDateMustBeAfterStartDate.printStackTrace();
        } catch (InvalidFactor invalidFactor) {
            invalidFactor.printStackTrace();
        } catch (InvalidMinClosePercentage invalidMinClosePercentage) {
            invalidMinClosePercentage.printStackTrace();
        }
    }

    public Collection<User> getDonnorsByProjectId(Integer id) {
        return findByID(id).getUsers();
    }

    public void updateProjectService(Integer projectId, Integer projectStateId) {
        Project newProject = findByID(projectId);
        ProjectState newProjectState = projectStateService.findByID(projectStateId);
        newProject.setProjectState(newProjectState);
        save(newProject);
    }

    public void updateLocation(Integer locationId, Location location) {
        Location newLocation = locationService.findByID(locationId);
        try {
            newLocation.setPopulation(location.getPopulation());
            newLocation.setProvince(location.getProvince());
            locationService.save(newLocation);
        } catch (IntegerMustBePositive integerMustBePositive) {
            integerMustBePositive.printStackTrace();
        }
    }
}
