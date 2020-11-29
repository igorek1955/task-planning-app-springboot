package ru.todoapp.services;

import org.springframework.stereotype.Service;
import ru.todoapp.models.Project;
import ru.todoapp.repositories.ProjectRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ProjectService implements CrudService<Project, Long> {

    private final ProjectRepository projectRepository;

    public ProjectService(ProjectRepository projectRepository) {
        this.projectRepository = projectRepository;
    }


    @Override
    public List<Project> findAll() {
        List<Project> projects = new ArrayList<>();
        projectRepository.findAll().forEach(projects::add);
        return projects;
    }

    @Override
    public Project findById(Long aLong) {
        Optional<Project> project = projectRepository.findById(aLong);
        if(!project.isPresent()){
            throw new NullPointerException("Project with this id was not found : " + aLong.toString());
        }
        return project.get();
    }

    @Override
    public Project save(Project object) {
        return projectRepository.save(object);
    }

    @Override
    public void delete(Project object) {
        projectRepository.delete(object);
    }

    @Override
    public void deleteById(Long aLong) {
        projectRepository.deleteById(aLong);
    }

    @Override
    public Project findByName(String name) {
        return projectRepository.findByName(name).get();
    }
}
