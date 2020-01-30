package com.taskmanagementsystem.controller;

import com.taskmanagementsystem.db.SetOfTasksRepository;
import com.taskmanagementsystem.db.TaskRepository;
import com.taskmanagementsystem.db.UserRepository;
import com.taskmanagementsystem.model.SetOfTasks;
import com.taskmanagementsystem.model.Task;
import com.taskmanagementsystem.model.User;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.validation.Valid;
import java.util.*;

@RestController
@RequestMapping("users")
@CrossOrigin
public class UserController {
    private UserRepository userRepository;
    private SetOfTasksRepository setOfTasksRepository;
    private TaskRepository taskRepository;
    private PasswordEncoder passwordEncoder;

    public UserController(UserRepository userRepository, SetOfTasksRepository setOfTasksRepository, TaskRepository taskRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.setOfTasksRepository = setOfTasksRepository;
        this.taskRepository = taskRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @GetMapping("all")
    public List<User> getAll() {
        return userRepository.findAll();
    }

    @GetMapping
    public List<User> getByEmail(@PathVariable String email) {
        return userRepository.findAllByEmail(email);
    }

    @GetMapping("me")
    public User getByUsername(@RequestParam String username) {
        return userRepository.findByUsername(username);
    }

    @PostMapping("create")
    public String createUser(@Valid @RequestBody User user){
        List<User> allByEmail = userRepository.findAllByEmail(user.getEmail());
        List<User> allByUsername = userRepository.findAllByUsername(user.getUsername());

        if(allByEmail.isEmpty() && allByUsername.isEmpty()) {
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            userRepository.save(user);

            List<SetOfTasks> setOfTasksList = new ArrayList<>();
            setOfTasksList.add(new SetOfTasks("Inbox", user));
            setOfTasksRepository.saveAll(setOfTasksList);

            Task defaultTask = new Task(new Date(), "Simple task.", false, setOfTasksList.get(0),
            "Click on task title to edit.");
            taskRepository.save(defaultTask);

            return "Done";
        }

        if(!allByEmail.isEmpty())
            return "Email is already used.";

        return "Username is already used.";
    }

    @PostMapping("changePass")
    public String changePassword(@RequestBody Map<String,String> body){
        Optional<User> dbUser = userRepository.findById(Integer.valueOf(body.get("id")));
        if(dbUser.isPresent()){
            User user = dbUser.get();
            if(passwordEncoder.matches(body.get("oldPass"), user.getPassword())){
                user.setPassword(passwordEncoder.encode(body.get("newPass")));
                userRepository.save(user);
                return "Password successfully updated";
            }
            else return "Old password is invalid";
        }
        return "User doesn't exist.";
    }

    @PostMapping("save")
    public void save(@RequestBody User user){
        Optional<User> dbUser = userRepository.findById(user.getId());
        if(dbUser.isPresent()){
            User user1 = dbUser.get();
            user1.setEmail(user.getEmail());
            user1.setPhoto(user.getPhoto());
            user1.setFirstName(user.getFirstName());
            user1.setLastName(user.getLastName());
            userRepository.save(user1);
        }
    }

    @DeleteMapping("delete")
    public void deleteUser(@RequestBody User user){
        userRepository.delete(user);
    }
}
