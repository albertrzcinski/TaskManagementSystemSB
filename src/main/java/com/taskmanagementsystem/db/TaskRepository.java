package com.taskmanagementsystem.db;

import com.taskmanagementsystem.model.SetOfTasks;
import com.taskmanagementsystem.model.Task;
import com.taskmanagementsystem.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TaskRepository extends JpaRepository<Task, Integer> {
    List<Task> findAllBySetOfTasks(SetOfTasks setOfTasks);
    List<Task> findAllBySetOfTasksIn(List<SetOfTasks> setOfTasksList);
    List<Task> findAllByMembers(User user);
}
