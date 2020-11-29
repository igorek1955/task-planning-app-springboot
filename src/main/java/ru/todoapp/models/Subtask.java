package ru.todoapp.models;


import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@Table(name = "subtasks")
public class Subtask {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "description")
    @NotBlank
    private String description;

    @ManyToOne
    private Task task;


    @OneToMany
    private List<Task> subtasks = new ArrayList<>();

    public void addSubtask(Task subtask){
        subtasks.add(subtask);
    }
}