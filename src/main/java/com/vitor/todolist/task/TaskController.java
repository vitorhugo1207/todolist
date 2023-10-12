package com.vitor.todolist.task;

import java.time.LocalDateTime;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/task")
public class TaskController {

    @Autowired
    private TaskRepository taskRepository;

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
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("The start date / end date must be major than current date");
        }
        
        // Verify task end date is bigger than task start date
        if (taskModel.getEndAt().isBefore(taskModel.getStartAt())) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("The end date must be major than start date");
        }


        var task = this.taskRepository.save(taskModel);
        return ResponseEntity.status(HttpStatus.CREATED).body(task);
    }
    
}
