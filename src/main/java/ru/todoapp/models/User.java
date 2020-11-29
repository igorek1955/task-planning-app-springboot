package ru.todoapp.models;

import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;


    @Column(name = "name")
    @NotEmpty
    private String name;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "user")
    private List<Task> tasks = new ArrayList<>();

    @ManyToOne
    private Project project;


    public void addTask(Task task){
        tasks.add(task);
    }
}
