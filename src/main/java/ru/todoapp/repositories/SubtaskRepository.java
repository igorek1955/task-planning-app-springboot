package ru.todoapp.repositories;

import org.springframework.data.repository.CrudRepository;
import ru.todoapp.models.Subtask;
import ru.todoapp.models.Task;

import java.util.Optional;

public interface SubtaskRepository extends CrudRepository<Subtask, Long> {
    Optional<Subtask> findByDescription(String name);
}
