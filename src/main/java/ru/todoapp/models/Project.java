package ru.todoapp.models;

import lombok.Data;
import lombok.ToString;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@Table(name = "projects")
public class Project {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotBlank
    @Column(name = "name")
    private String name;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "project")
    private List<Task> tasks = new ArrayList<>();

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "project")
    private List<User> users = new ArrayList<>();

    public void addTask(Task task){
        tasks.add(task);
    }

    public void addUser(User user){
        users.add(user);
    }
}
