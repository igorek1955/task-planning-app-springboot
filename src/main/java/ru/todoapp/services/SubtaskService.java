package ru.todoapp.services;

import org.springframework.stereotype.Service;
import ru.todoapp.models.Project;
import ru.todoapp.models.Subtask;
import ru.todoapp.models.Task;
import ru.todoapp.models.User;
import ru.todoapp.repositories.ProjectRepository;
import ru.todoapp.repositories.SubtaskRepository;
import ru.todoapp.repositories.TaskRepository;
import ru.todoapp.repositories.UserRepository;

import java.util.*;

@Service
public class SubtaskService implements CrudService<Subtask, Long> {

    private final SubtaskRepository taskRepository;

    public SubtaskService(ProjectRepository projectRepository, UserRepository userRepository, SubtaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    @Override
    public List<Subtask> findAll() {
        List<Subtask> tasks = new ArrayList<>();
        taskRepository.findAll().forEach(tasks::add);
        return tasks;
    }

    @Override
    public Subtask findById(Long aLong) {
        Optional<Subtask> task = taskRepository.findById(aLong);
        if (!task.isPresent()) {
            throw new NullPointerException("Task with this id was not found : " + aLong.toString());
        }
        return task.get();
    }

    @Override
    public Subtask save(Subtask object) {
        return taskRepository.save(object);
    }

    @Override
    public void delete(Subtask object) {
        taskRepository.delete(object);
    }

    @Override
    public void deleteById(Long aLong) {
        Optional<Subtask> optionalTask = taskRepository.findById(aLong);
        optionalTask.ifPresent(taskRepository::delete);
    }

    @Override
    public Subtask findByName(String name) {
        return taskRepository.findByDescription(name).get();
    }
}
