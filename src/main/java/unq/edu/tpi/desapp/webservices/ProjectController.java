package unq.edu.tpi.desapp.webservices;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import unq.edu.tpi.desapp.model.Project;
import unq.edu.tpi.desapp.model.User;
import unq.edu.tpi.desapp.services.ProjectService;

import java.util.Collection;
import java.util.List;

@RestController
@EnableAutoConfiguration
public class ProjectController {

    @Autowired
    private ProjectService projectService;

    @GetMapping("/projects")
    public List<Project> allProjects() {
        return projectService.findAll();
    }

    @PostMapping("/projects")
    public void createProject(@RequestBody Project project) {
        projectService.createProject(project);
    }

    @GetMapping("/projects/{id}")
    public Project getProject(@PathVariable("id") Integer id) {
        return projectService.findByID(id);
    }

    @PutMapping("/projects/{id}")
    @Transactional
    public void updateProject(@RequestBody Project project, @PathVariable("id") Integer id) {
        System.out.printf(project.getName());

        projectService.updateProject(id, project);
    }

    @GetMapping("/projects/{id}/donors")
    public Collection<User> getDonors(@PathVariable("id") Integer id) {
        return projectService.getDonnorsByProjectId(id);
    }
}
