package com.taskmanagementsystem.controller;

import com.taskmanagementsystem.db.SetOfTasksRepository;
import com.taskmanagementsystem.db.TaskRepository;
import com.taskmanagementsystem.model.SetOfTasks;
import com.taskmanagementsystem.model.Task;
import com.taskmanagementsystem.model.User;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("tasks")
public class TaskController {
    private TaskRepository taskRepository;
    private SetOfTasksRepository setOfTasksRepository;

    public TaskController(TaskRepository taskRepository, SetOfTasksRepository setOfTasksRepository) {
        this.taskRepository = taskRepository;
        this.setOfTasksRepository = setOfTasksRepository;
    }

    @GetMapping("all")
    public List<Task> getAll(){
        return taskRepository.findAll();
    }


    @GetMapping("allBySet")
    public List<Task> getAllBySet(@RequestBody SetOfTasks setOfTasks){
        return taskRepository.findAllBySetOfTasks(setOfTasks);
    }

    @GetMapping("/byId/{id}")
    public Optional<Task> getById(@PathVariable Integer id){
        return taskRepository.findById(id);
                /* TODO Add exceptions to all controllers
                .orElseThrow(() -> resourcenotfoundexception);
                 */
    }

    // wyswietla tylko utworzone przeze mnie
    @GetMapping("allByOwner")
    public List<Task> getAllByOwner(@RequestBody User user){
        List<SetOfTasks> setOfTasksList = setOfTasksRepository.findAllByOwner(user);
        List<Task> allByOwner = taskRepository.findAllBySetOfTasksIn(setOfTasksList);
        if(allByOwner.isEmpty()) {
            // fill the gap with exception
            return null;
        }
        else
            return allByOwner;
    }

    // wyswietla wszystkie udostepnione dla mnie
    @GetMapping("allByMember")
    public List<Task> getAllByMember(@RequestBody User user){
        return taskRepository.findAllByMembers(user);
    }

    @GetMapping("save")
    public void saveTask(@RequestBody Task task){
        taskRepository.save(task);
    }

    //can change to deleteById
    @DeleteMapping("delete")
    public void deleteTask(@RequestBody Task task){
        taskRepository.delete(task);
    }
}
