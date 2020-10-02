package unq.edu.tpi.desapp.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import unq.edu.tpi.desapp.model.ProjectState;
import unq.edu.tpi.desapp.repositories.ProjectStateRepository;

import java.util.List;

@Service
public class ProjectStateService {

    @Autowired
    private ProjectStateRepository projectStateRepository;

    @Transactional
    public ProjectState save(ProjectState model) {
        return this.projectStateRepository.save(model);
    }

    public ProjectState findByID(Integer id) {
        return this.projectStateRepository.findById(id).get();
    }

    @Transactional
    public List<ProjectState> findAll() {
        return this.projectStateRepository.findAll();
    }

}
