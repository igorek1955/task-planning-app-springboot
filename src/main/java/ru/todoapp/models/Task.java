package ru.todoapp.models;


import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@Table(name = "tasks")
public class Task {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "description")
    @NotBlank
    private String description;

    @ManyToOne
    private Project project;

    @ManyToOne
    private User user;

    @OneToMany
    private List<Subtask> subtasks = new ArrayList<>();

    public void addSubtask(Subtask subtask){
        subtasks.add(subtask);
    }
}
