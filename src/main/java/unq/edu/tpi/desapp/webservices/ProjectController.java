package unq.edu.tpi.desapp.webservices;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import unq.edu.tpi.desapp.model.Project;
import unq.edu.tpi.desapp.services.ProjectService;

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

    @GetMapping("/projects/{id}")
    public Project getProject(@PathVariable("id") Integer id) {
        return projectService.findByID(id);
    }
}
