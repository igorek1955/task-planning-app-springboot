package ru.todoapp.controllers;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.todoapp.models.Task;
import ru.todoapp.services.ProjectService;
import ru.todoapp.services.SubtaskService;
import ru.todoapp.services.TaskService;
import ru.todoapp.services.UserService;
import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Controller
public class TaskController {

    @Autowired
    TaskService taskService;

    @Autowired
    ProjectService projectService;

    @Autowired
    UserService userService;


    @GetMapping({"/", "", "tasks", "/tasks"})
    public String getTasks(Model model) {
        List<Task> tasks = taskService.getTasks()
                .stream()
                .filter(task -> !task.getIsSubtask())
                .collect(Collectors.toList());
        model.addAttribute("tasks", tasks);
        return "tasks";
    }

    @GetMapping("create")
    public String newTask(Model model) {
        model.addAttribute("task", new Task());
        return "/taskForm";
    }

    @PostMapping("create")
    public String createTask(@Valid Task task, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            bindingResult.getAllErrors().forEach(objectError -> {
                log.debug(objectError.toString());
            });
        }

        userService.save(task.getUser());
        projectService.save(task.getProject());
        taskService.save(task);
        return "redirect:/tasks";
    }

    @GetMapping("/tasks/{taskId}/edit")
    public String editTask(@PathVariable String taskId, Model model) {
        model.addAttribute("task", taskService.findById(Long.valueOf(taskId)));
        return "/taskForm";
    }

    @PutMapping("/tasks/{taskId}/edit")
    public String processEditTask(@Valid Task task, BindingResult bindingResult, @PathVariable Long taskId) {
        if (bindingResult.hasErrors()) {
            bindingResult.getAllErrors().forEach(objectError -> {
                log.debug(objectError.toString());
            });
        }

        Task savedTask = taskService.save(task);
        return "redirect:/tasks/" + savedTask.getId();
    }

    @RequestMapping("/tasks/{taskId}/delete")
    public String deleteTask(@PathVariable String taskId) {
        taskService.deleteById(Long.valueOf(taskId));
        return "redirect:/tasks";
    }

    @GetMapping("/report-form")
    public String openReportForm() {
        return "/reportForm";
    }

    @GetMapping("/generate-report")
    public String submitReportForm(@RequestParam(name = "project") String project, @RequestParam(name = "user") String user) {
        if (project.isEmpty() && user.isEmpty()) {
            taskService.exportAll();
            return "/exportSuccess";
        }
        taskService.exportData(project, user);
        return "/exportSuccess";
    }

    @GetMapping("/tasks/{taskId}/subtasks")
    public String checkSubtasks(@PathVariable String taskId, Model model) {
        Task task = taskService.findById(Long.valueOf(taskId));
        model.addAttribute("subtasks", task.getSubtasks());
        model.addAttribute("task", task);
        if (task.getSubtasks().size() == 0) {
            model.addAttribute("message", "This task has no subtasks");
        } else{
            model.addAttribute("message", "This task has " + task.getSubtasks().size() + " subtasks");
        }
        model.addAttribute("subtask", new Task());
        return "/subtasks";
    }

    @PostMapping("/tasks/{taskId}/subtasks")
    public String submitSubtask(@PathVariable String taskId, Task subtask, Model model){
        Task savedTask = taskService.findById(Long.valueOf(taskId));
        subtask.setProject(savedTask.getProject());
        subtask.setUser(savedTask.getUser());
        subtask.setIsSubtask(true);
        taskService.save(subtask);
        savedTask.addSubtask(subtask);
        taskService.save(savedTask);
        return "redirect:/tasks/" + taskId + "/subtasks";
    }
}
