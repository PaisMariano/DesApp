package unq.edu.tpi.desapp.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import unq.edu.tpi.desapp.model.Location;
import unq.edu.tpi.desapp.model.Project;
import unq.edu.tpi.desapp.model.ProjectState;
import unq.edu.tpi.desapp.model.User;
import unq.edu.tpi.desapp.model.exceptions.EndDateMustBeAfterStartDate;
import unq.edu.tpi.desapp.model.exceptions.InvalidFactor;
import unq.edu.tpi.desapp.model.exceptions.InvalidMinClosePercentage;
import unq.edu.tpi.desapp.repositories.ProjectRepository;

import java.time.LocalDate;
import java.util.Collection;
import java.util.List;

@Service
public class ProjectService {

    @Autowired
    private ProjectRepository projectRepository;
    @Autowired
    private LocationService locationService;

    @Transactional
    public Project save(Project project) {
        return projectRepository.save(project);
    }

    public Project findByID(Integer id) {
        return projectRepository.findById(id).get();
    }

    public List<Project> findAll() {
        return projectRepository.findAll();
    }

    public Collection<User> findDonorsByProjectID(Integer id) {
        return findByID(id).getUsers();
    }

    public void createProject(String name, Integer factor, Float percentage, LocalDate startDate, LocalDate endDate, Integer locationId) {
        try {
            Location location = locationService.findByID(locationId);
            Project project = new Project(name, factor, percentage, startDate, endDate, location);
            save(project);
        } catch (EndDateMustBeAfterStartDate endDateMustBeAfterStartDate) {
            endDateMustBeAfterStartDate.printStackTrace();
        } catch (InvalidFactor invalidFactor) {
            invalidFactor.printStackTrace();
        } catch (InvalidMinClosePercentage invalidMinClosePercentage) {
            invalidMinClosePercentage.printStackTrace();
        }
    }

    public void updateProject(Integer projectId, String name, Integer factor, Float percentage, LocalDate startDate, LocalDate endDate, ProjectState state, Integer locationId) {
        try {
            Location location = locationService.findByID(locationId);
            Project project = findByID(projectId);
            project.setName(name);
            project.setFactor(factor);
            project.setMinClosePercentage(percentage);
            project.setStartDate(startDate);
            project.setEndDate(endDate);
            project.setProjectState(state);
            project.setLocation(location);
            save(project);
        } catch (EndDateMustBeAfterStartDate endDateMustBeAfterStartDate) {
            endDateMustBeAfterStartDate.printStackTrace();
        } catch (InvalidFactor invalidFactor) {
            invalidFactor.printStackTrace();
        } catch (InvalidMinClosePercentage invalidMinClosePercentage) {
            invalidMinClosePercentage.printStackTrace();
        }

    }
}
