package ru.todoapp.bootstrap;


import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import ru.todoapp.models.Project;
import ru.todoapp.models.Task;
import ru.todoapp.models.User;
import ru.todoapp.repositories.ProjectRepository;
import ru.todoapp.repositories.TaskRepository;
import ru.todoapp.repositories.UserRepository;

/*
This class automatically loads data into database
 */

@Component
public class InitData implements ApplicationListener<ContextRefreshedEvent> {


    private final ProjectRepository projectService;
    private final TaskRepository taskService;
    private final UserRepository userService;

    public InitData(ProjectRepository projectService, TaskRepository taskService, UserRepository userService){
        this.projectService = projectService;
        this.taskService = taskService;
        this.userService = userService;
    }


    @Override
    @Transactional
    public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {
        initData();
    }

    private void initData() {
        User user1 = new User();
        user1.setName("Petya");

        User user2 = new User();
        user2.setName("Vanya");

        Project project1 = new Project();
        project1.setName("Banking");

        Project project2 = new Project();
        project2.setName("Construction");

        Task task1 = new Task();
        task1.setDescription("create database");

        Task task2 = new Task();
        task2.setDescription("buy construction hat");

        user1.setProject(project1);
        user1.addTask(task1);
        project1.addTask(task1);
        project1.addUser(user1);
        task1.setProject(project1);
        task1.setUser(user1);
        projectService.save(project1);
        taskService.save(task1);
        userService.save(user1);

        user2.setProject(project2);
        user2.addTask(task2);
        project2.addTask(task2);
        project2.addUser(user2);
        task2.setProject(project2);
        task2.setUser(user2);
        projectService.save(project2);
        taskService.save(task2);
        userService.save(user2);
    }
}
