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

    public void createProject(Project project) {
        Location location = locationService.findByID(project.getLocation().getId());
        project.setLocation(location);
        save(project);
    }

    public void updateProject(Integer projectId, Project project) {
        try {
            Location newLocation = locationService.findByID(project.getLocation().getId());
            Project newProject = findByID(projectId);
            newProject.setName(project.getName());
            newProject.setFactor(project.getFactor());
            newProject.setMinClosePercentage(project.getMinClosePercentage());
            newProject.setStartDate(project.getStartDate());
            newProject.setEndDate(project.getEndDate());
            newProject.setProjectState(project.getProjectState());
            newProject.setLocation(newLocation);
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
}
