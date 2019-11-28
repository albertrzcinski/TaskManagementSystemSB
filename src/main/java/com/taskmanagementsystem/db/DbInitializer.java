package com.taskmanagementsystem.db;

import com.taskmanagementsystem.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class DbInitializer implements CommandLineRunner {

    private UserRepository userRepository;
    private SetOfTasksRepository setOfTasksRepository;
    private TaskRepository taskRepository;

    @Autowired
    public DbInitializer(UserRepository userRepository, SetOfTasksRepository setOfTasksRepository, TaskRepository taskRepository) {
        this.userRepository = userRepository;
        this.setOfTasksRepository = setOfTasksRepository;
        this.taskRepository = taskRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        taskRepository.deleteAll();
        setOfTasksRepository.deleteAll();
        userRepository.deleteAll();

        List<User> userList = new ArrayList<>();

        userList.add(new User("albert@int.pl", "albert3233", "Albert", "Trzcinski", "UTC", "alb123"));
        userRepository.saveAll(userList);
    }
}
