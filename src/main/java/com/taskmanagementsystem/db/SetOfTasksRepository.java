package com.taskmanagementsystem.db;

import com.taskmanagementsystem.model.SetOfTasks;
import com.taskmanagementsystem.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SetOfTasksRepository extends JpaRepository<SetOfTasks, Integer> {
    List<SetOfTasks> findAllByOwner(User user);
    List<SetOfTasks> findAllByName(String name);
}
