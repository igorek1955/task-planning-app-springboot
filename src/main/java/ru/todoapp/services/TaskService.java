package ru.todoapp.services;

import org.springframework.stereotype.Service;
import ru.todoapp.models.Project;
import ru.todoapp.models.Task;
import ru.todoapp.models.User;
import ru.todoapp.repositories.ProjectRepository;
import ru.todoapp.repositories.TaskRepository;
import ru.todoapp.repositories.UserRepository;

import java.util.*;

@Service
public class TaskService implements CrudService<Task, Long> {

    private final ProjectRepository projectRepository;

    private final UserRepository userRepository;

    private final TaskRepository taskRepository;

    public TaskService(ProjectRepository projectRepository, UserRepository userRepository, TaskRepository taskRepository) {
        this.projectRepository = projectRepository;
        this.userRepository = userRepository;
        this.taskRepository = taskRepository;
    }


    @Override
    public List<Task> findAll() {
        List<Task> tasks = new ArrayList<>();
        taskRepository.findAll().forEach(tasks::add);
        return tasks;
    }

    @Override
    public Task findById(Long aLong) {
        Optional<Task> task = taskRepository.findById(aLong);
        if (!task.isPresent()) {
            throw new NullPointerException("Task with this id was not found : " + aLong.toString());
        }
        return task.get();
    }

    @Override
    public Task save(Task object) {
        return taskRepository.save(object);
    }

    @Override
    public void delete(Task object) {
        taskRepository.delete(object);
    }

    @Override
    public void deleteById(Long aLong) {
        Optional<Task> optionalTask = taskRepository.findById(aLong);
        optionalTask.ifPresent(taskRepository::delete);
    }

    @Override
    public Task findByName(String name) {
        return taskRepository.findByDescription(name).get();
    }

    public Boolean foundProjectCollisions(Task task) {
        Project project = task.getProject();
        Optional<Project> savedProject = projectRepository.findByName(project.getName());
        return savedProject.isPresent();
    }

    public Boolean foundUserCollisions(Task task) {
        User user = task.getUser();
        Optional<User> savedUser = userRepository.findByName(user.getName());
        return savedUser.isPresent();
    }

    public Set<Task> getTasks() {
        Set<Task> taskSet = new HashSet<>();
        taskRepository.findAll().iterator().forEachRemaining(taskSet::add);
        return taskSet;
    }

    public void exportAll() {
        ArrayList<Task> tasks = new ArrayList<>();
        taskRepository.findAll().forEach(tasks::add);
        DataExportService.writeToCSV(tasks);
    }

    public void exportData(String projectName, String userName) {
        ArrayList<Task> tasks = new ArrayList<>();

        Optional<Project> projectOptional = projectRepository.findByName(projectName);
        Optional<User> userOptional = userRepository.findByName(userName);

        if (projectOptional.isEmpty() && userOptional.isEmpty()) {
            taskRepository.findAll().forEach(tasks::add);
        } else if (projectOptional.isEmpty()) {
            User user = userOptional.get();
            taskRepository.findAll().forEach(task -> {
                if (task.getUser().getId().equals(user.getId())) {
                    tasks.add(task);
                }
            });
        } else if (userOptional.isEmpty()) {
            Project project = projectOptional.get();
            taskRepository.findAll().forEach(task -> {
                if (task.getProject().getId().equals(project.getId())) {
                    tasks.add(task);
                }
            });
        } else {
            User user = userOptional.get();
            Project project = projectOptional.get();
            taskRepository.findAll().forEach(task -> {
                if (task.getUser().getId().equals(user.getId()) && task.getProject().getId().equals(project.getId())) {
                    tasks.add(task);
                }
            });
        }

        DataExportService.writeToCSV(tasks);
    }
}
