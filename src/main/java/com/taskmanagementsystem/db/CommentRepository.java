package com.taskmanagementsystem.db;

import com.taskmanagementsystem.model.Comment;
import com.taskmanagementsystem.model.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Integer> {
    List<Comment> findAllByTask(Task task);
}
