package com.taskmanagementsystem.controller;

import com.taskmanagementsystem.db.CommentRepository;
import com.taskmanagementsystem.db.TaskRepository;
import com.taskmanagementsystem.model.Comment;
import com.taskmanagementsystem.model.Task;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("comments")
@CrossOrigin
public class CommentController {
    private CommentRepository commentRepository;
    private TaskRepository taskRepository;

    public CommentController(CommentRepository commentRepository, TaskRepository taskRepository) {
        this.commentRepository = commentRepository;
        this.taskRepository = taskRepository;
    }

    @GetMapping("byTask")
    public List<Comment> getByTask(@RequestParam Integer taskId) {
        Optional<Task> task = taskRepository.findById(taskId);
        return task.map(value -> commentRepository.findAllByTask(value)).orElse(null);
    }

    @PostMapping("save")
    public void saveComment(@RequestBody Comment comment) {
        commentRepository.save(comment);
    }

    @DeleteMapping("delete")
    public void deleteComment(@RequestParam Integer id) {
        commentRepository.deleteById(id);
    }
}
