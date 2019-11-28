package com.taskmanagementsystem.controller;

import com.taskmanagementsystem.db.SetOfTasksRepository;
import com.taskmanagementsystem.db.TaskRepository;
import com.taskmanagementsystem.db.UserRepository;
import com.taskmanagementsystem.model.SetOfTasks;
import com.taskmanagementsystem.model.Task;
import com.taskmanagementsystem.model.User;
import org.springframework.web.bind.annotation.*;
// import org.springframework.security.crypto.password.PasswordEncoder;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("users")
public class UserController {
    private UserRepository userRepository;
    private SetOfTasksRepository setOfTasksRepository;
    private TaskRepository taskRepository;
    // private PasswordEncoder passwordEncoder;

    public UserController(UserRepository userRepository, SetOfTasksRepository setOfTasksRepository, TaskRepository taskRepository) {//, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.setOfTasksRepository = setOfTasksRepository;
        this.taskRepository = taskRepository;
        //this.passwordEncoder = passwordEncoder;
    }

    @GetMapping("all")
    public List<User> getAll() {
        return userRepository.findAll();
    }

    @GetMapping
    public List<User> getByEmail(@PathVariable String email) {
        return userRepository.findAllByEmail(email);
    }

    //TODO R --> Valid
    @PostMapping("create")
    public void createUser(@Valid @RequestBody User user){
        if(userRepository.findAllByEmail(user.getEmail()).isEmpty()) {
            // user.setPassword(passwordEncoder.encode(user.getPassword()));
            userRepository.save(user);

            //create default sets for new user
            List<SetOfTasks> setOfTasksList = new ArrayList<>();
            setOfTasksList.add(new SetOfTasks("Inbox", user));
            setOfTasksList.add(new SetOfTasks("Complete", user));
            setOfTasksRepository.saveAll(setOfTasksList);

            Task defaultTask = new Task(new Date(), "New task", false, setOfTasksList.get(0));
            taskRepository.save(defaultTask);
        }
    }

    // this may be save if setPassword won't encode twice or user has id in RequestBody
    @PostMapping("pass")
    public void changePassword(@RequestBody User user){
        if(!userRepository.findAllByEmail(user.getEmail()).isEmpty()){
             // user.setPassword(passwordEncoder.encode(user.getPassword()));
            userRepository.save(user);
        }
    }

    @PostMapping("save")
    public void save(@RequestBody User user){
        if(!userRepository.findAllByEmail(user.getEmail()).isEmpty())
            userRepository.save(user);
    }

    @DeleteMapping("delete")
    public void deleteUser(@RequestBody User user){
        userRepository.delete(user);
    }
}
