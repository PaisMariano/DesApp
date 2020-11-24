package unq.edu.tpi.desapp.webservices;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import unq.edu.tpi.desapp.model.*;
import unq.edu.tpi.desapp.exceptions.BadRequestException;
import unq.edu.tpi.desapp.exceptions.ElementAlreadyExists;
import unq.edu.tpi.desapp.services.ProjectService;
import unq.edu.tpi.desapp.exceptions.ProjectNotFoundException;
import unq.edu.tpi.desapp.exceptions.UserNotFoundException;

import java.util.Collection;
import java.util.List;
import java.util.Map;

@RestController
@EnableAutoConfiguration
public class ProjectController {

    @Autowired
    private ProjectService projectService;

    //PROJECT METHODS
    @GetMapping("/projects/page")
    public List<Project> allProjects(
            @RequestParam Integer from,
            @RequestParam Integer to) throws BadRequestException {

        return projectService.findAllProjectsWithIndexes(from, to);
    }

    @GetMapping("/projects")
    public List<Project> allProjects() {
        return projectService.findAllProjects();
    }

    @PostMapping("/projects")
    public ResponseEntity<String> createProject(@RequestBody Project project) throws BadRequestException, ElementAlreadyExists{
        projectService.createProject(project);

        return ResponseEntity.status(HttpStatus.CREATED).body("Created");
    }

    @GetMapping("/projects/{id}")
    public Project getProject(@PathVariable("id") Integer id) throws ProjectNotFoundException {
        return projectService.findByID(id);
    }

    @PutMapping("/projects/{id}")
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

    @PostMapping("/project_state")
    public ResponseEntity<String> updateProjectState(@RequestBody Map<String, Integer> json) throws ProjectNotFoundException {

        Integer projectId = json.get("projectId");
        Integer stateId = json.get("stateId");
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

    @GetMapping("/locations/arsat")
    public List<ArsatLocation> allArsatLocations() { return projectService.getAllArsatLocations(); }

    @PutMapping("/locations/{id}")
    public ResponseEntity<String> updateLocation(
            @RequestBody Location location,
            @PathVariable("id") Integer id) throws BadRequestException {

        projectService.updateLocation(id, location);
        return ResponseEntity.status(HttpStatus.OK).body("Resource updated successfully");
    }

    @GetMapping("/locations/leastdonated")
    public List<String> dailyLeastTenDonatedLocations() {
        return projectService.dailyLeastTenDonatedLocations();
    }

    //DONATION

    @PostMapping("/donations/project/{projectId}/user/{userId}")
    public ResponseEntity<String> createDonation(
            @PathVariable("projectId") Integer projectId,
            @PathVariable("userId") Integer userId,
            @RequestBody Donation donation) throws ProjectNotFoundException, UserNotFoundException, BadRequestException {

        projectService.createDonation(projectId, userId, donation);

        return ResponseEntity.status(HttpStatus.CREATED).body("Created");
    }

    @GetMapping("/donations/topten")
    public List<String> dailyTopTenDonations(){
        return projectService.dailyTopTenDonations();
    }
}
