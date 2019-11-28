package com.taskmanagementsystem.controller;

import com.taskmanagementsystem.db.CommentRepository;
import com.taskmanagementsystem.model.Comment;
import com.taskmanagementsystem.model.Task;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("comments")
public class CommentController {
    private CommentRepository commentRepository;

    public CommentController(CommentRepository commentRepository) {
        this.commentRepository = commentRepository;
    }

    @GetMapping("byTask")
    public List<Comment> getByTask(@RequestBody Task task) {
        return commentRepository.findAllByTask(task);
    }

    @GetMapping("save")
    public void saveComment(@RequestBody Comment comment) {
        commentRepository.save(comment);
    }

    @DeleteMapping("delete")
    public void deleteComment(@RequestBody Comment comment) {
        commentRepository.delete(comment);
    }
}
