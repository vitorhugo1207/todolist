package com.vitor.todolist.task;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.vitor.todolist.utils.Utils;

import jakarta.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/task")
public class TaskController {

    @Autowired
    private TaskRepository taskRepository;

    public ResponseEntity responseStatusCode() {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("You doesn't have this task");
    }

    @PostMapping("/")
    /*
     * Receve in HttpServletRequest request userID
    */
    public ResponseEntity create(@RequestBody TaskModel taskModel, HttpServletRequest request) {
        taskModel.setIdUser((UUID) request.getAttribute("idUser"));

        // Verify tittle length
        if (taskModel.getTitle().length() > 50) {
            System.out.println("Tittle larger than 50 characters");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Tittle larger than 50 characters");
        }

        // Verify task start date with current date 
        var currentDate = LocalDateTime.now();
        if (currentDate.isAfter(taskModel.getStartAt()) || currentDate.isAfter(taskModel.getEndAt())) { // if actual data if bigger than Start Data or if actual data if bigger than Before Data 
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("The start date / end date must be major than current date");
        }

        // Verify task end date is bigger than task start date
        if (taskModel.getEndAt().isBefore(taskModel.getStartAt())) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("The end date must be major than start date");
        }

        var task = this.taskRepository.save(taskModel);
        return ResponseEntity.status(HttpStatus.CREATED).body(task);
    }

    @GetMapping("/")
    public List<TaskModel> list(HttpServletRequest request) {
        var idUser = (UUID) request.getAttribute("idUser");
        var tasks = this.taskRepository.findByIdUser(idUser);
        return tasks;
    }

    /*
     * Suppose: http://localhost:8080/tasks/34324-6fsddf3-435fv54
     * 34324-6fsddf3-435fv54 is id of task, so:
     * in @PathVariable UUID idTask we'll recive this id (34324-6fsddf3-435fv54)
     * And @PutMapping("/{idTask}")
    */
    @PutMapping("/{idTask}") // Receve the path, must be de same in @PathVariable UUID idTask
    public TaskModel update(@RequestBody TaskModel taskModel, HttpServletRequest request, @PathVariable UUID idTask) {

        var idUser = (UUID) request.getAttribute("idUser");
        if (idUser != idTask) {
            responseStatusCode();
            return null;
        }

        var task = this.taskRepository.findById(idTask).orElse(null);
        Utils.copyNonNullProperties(taskModel, task); // if not pass a properies, keeps date already in database

        return this.taskRepository.save(task);
    }
}
