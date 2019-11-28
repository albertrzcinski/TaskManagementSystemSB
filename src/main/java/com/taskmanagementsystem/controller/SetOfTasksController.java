package com.taskmanagementsystem.controller;

import com.taskmanagementsystem.db.SetOfTasksRepository;
import com.taskmanagementsystem.model.SetOfTasks;
import com.taskmanagementsystem.model.User;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("sets")
public class SetOfTasksController {
    private SetOfTasksRepository setOfTasksRepository;

    public SetOfTasksController(SetOfTasksRepository setOfTasksRepository) {
        this.setOfTasksRepository = setOfTasksRepository;
    }

    @GetMapping("all")
    public List<SetOfTasks> getAll() {
        return setOfTasksRepository.findAll();
    }

    @GetMapping("byUser")
    public List<SetOfTasks> getAllByUser(@RequestBody User user) {
        return setOfTasksRepository.findAllByOwner(user);
    }

    @GetMapping("save")
    public void saveSet(@RequestBody SetOfTasks setOfTasks) {
        // if(setOfTasksRepository.getAllByName(setOfTasks.getName()) != null)
            setOfTasksRepository.save(setOfTasks);
    }

    @DeleteMapping("delete")
    public void deleteSet(@RequestBody SetOfTasks setOfTasks){
        setOfTasksRepository.delete(setOfTasks);
    }
}
