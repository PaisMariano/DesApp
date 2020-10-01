package unq.edu.tpi.desapp.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import unq.edu.tpi.desapp.model.Project;
import unq.edu.tpi.desapp.repositories.ProjectRepository;

import java.util.List;

@Service
public class ProjectService {

    @Autowired
    private ProjectRepository projectRepository;

    @Transactional
    public Project save(Project model) {
        return this.projectRepository.save(model);
    }

    public Project findByID(Integer id) {
        return this.projectRepository.findById(id).get();
    }

    public List<Project> findAll() {
        return this.projectRepository.findAll();
    }
}
