package unq.edu.tpi.desapp.webservices.exceptions;

public class ProjectNotFoundException extends Exception {
    private String projectName;

    public static ProjectNotFoundException createWith(String projectName) {
        return new ProjectNotFoundException(projectName);
    }

    private ProjectNotFoundException(String projectName) {
        this.projectName = projectName;
    }

    @Override
    public String getMessage() {
        return "Project '" + projectName + "' not found";
    }
}