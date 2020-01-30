package com.taskmanagementsystem.controller;

import com.taskmanagementsystem.db.SetOfTasksRepository;
import com.taskmanagementsystem.db.UserRepository;
import com.taskmanagementsystem.model.SetOfTasks;
import com.taskmanagementsystem.model.User;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("sets")
@CrossOrigin
public class SetOfTasksController {
    private SetOfTasksRepository setOfTasksRepository;
    private UserRepository userRepository;

    public SetOfTasksController(SetOfTasksRepository setOfTasksRepository, UserRepository userRepository) {
        this.setOfTasksRepository = setOfTasksRepository;
        this.userRepository = userRepository;
    }

    @GetMapping("all")
    public List<SetOfTasks> getAll() {
        return setOfTasksRepository.findAll();
    }

    @GetMapping("byUser")
    public List<SetOfTasks> getAllByUser(@RequestParam Integer userId) {
        Optional<User> user = userRepository.findById(userId);
        return user.map(value -> setOfTasksRepository.findAllByOwner(value)).orElse(null);
    }

    @PostMapping("save")
    public void saveSet(@RequestBody SetOfTasks setOfTasks) {
            setOfTasksRepository.save(setOfTasks);
    }

    @DeleteMapping("delete")
    public void deleteSet(@RequestParam Integer id){
        setOfTasksRepository.deleteById(id);
    }
}
