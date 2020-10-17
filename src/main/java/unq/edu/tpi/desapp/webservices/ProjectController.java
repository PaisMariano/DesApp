package unq.edu.tpi.desapp.webservices;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import unq.edu.tpi.desapp.model.Location;
import unq.edu.tpi.desapp.model.Project;
import unq.edu.tpi.desapp.model.User;
import unq.edu.tpi.desapp.webservices.exceptions.BadRequestException;
import unq.edu.tpi.desapp.webservices.exceptions.ElementAlreadyExists;
import unq.edu.tpi.desapp.services.ProjectService;
import unq.edu.tpi.desapp.webservices.exceptions.ProjectNotFoundException;

import java.util.Collection;
import java.util.List;

@RestController
@EnableAutoConfiguration
public class ProjectController {

    @Autowired
    private ProjectService projectService;

    //PROJECT METHODS
    @GetMapping("/projects")
    public List<Project> allProjects() {
        return projectService.findAllProjects();
    }

    @PostMapping("/projects")
    @Transactional
    public ResponseEntity<String> createProject(@RequestBody Project project) throws BadRequestException, ElementAlreadyExists{
        projectService.createProject(project);

        return ResponseEntity.status(HttpStatus.CREATED).body("Created");
    }

    @GetMapping("/projects/{id}")
    public Project getProject(@PathVariable("id") Integer id) throws ProjectNotFoundException {
        return projectService.findByID(id);
    }

    @PutMapping("/projects/{id}")
    @Transactional
    public ResponseEntity<String> updateProject(
            @RequestBody Project project,
            @PathVariable("id") Integer id) throws ProjectNotFoundException, BadRequestException {

        projectService.updateProject(id, project);

        return ResponseEntity.status(HttpStatus.OK).body("Resource updated successfully");
    }

    @PutMapping("/projects/{projectId}/state/{stateId}")
    public ResponseEntity<String> updateState(
            @PathVariable("projectId") Integer projectId,
            @PathVariable("stateId") Integer stateId) throws ProjectNotFoundException {

        projectService.updateProjectService(projectId, stateId);
        return ResponseEntity.status(HttpStatus.OK).body("Resource updated successfully");
    }

    @GetMapping("/projects/{id}/donors")
    public Collection<User> getDonors(@PathVariable("id") Integer id) throws ProjectNotFoundException {
        return projectService.getDonnorsByProjectId(id);
    }

    //LOCATION METHODS
    @GetMapping("/locations")
    public List<Location> allLocations() {
        return projectService.findAllLocations();
    }

    @PutMapping("/locations/{id}")
    public ResponseEntity<String> updateLocation(
            @RequestBody Location location,
            @PathVariable("id") Integer id) throws BadRequestException {

        projectService.updateLocation(id, location);
        return ResponseEntity.status(HttpStatus.OK).body("Resource updated successfully");
    }
}
