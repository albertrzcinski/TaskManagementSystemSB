package com.taskmanagementsystem.controller;

import com.taskmanagementsystem.db.SetOfTasksRepository;
import com.taskmanagementsystem.db.TagRepository;
import com.taskmanagementsystem.db.TaskRepository;
import com.taskmanagementsystem.db.UserRepository;
import com.taskmanagementsystem.model.SetOfTasks;
import com.taskmanagementsystem.model.Tag;
import com.taskmanagementsystem.model.Task;
import com.taskmanagementsystem.model.User;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("tasks")
@CrossOrigin
public class TaskController {
    private TaskRepository taskRepository;
    private SetOfTasksRepository setOfTasksRepository;
    private UserRepository userRepository;
    private TagRepository tagRepository;

    public TaskController(TaskRepository taskRepository, SetOfTasksRepository setOfTasksRepository, UserRepository userRepository, TagRepository tagRepository) {
        this.taskRepository = taskRepository;
        this.setOfTasksRepository = setOfTasksRepository;
        this.userRepository = userRepository;
        this.tagRepository = tagRepository;
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
    public List<Task> getAllByOwner(@RequestParam Integer userId){
        Optional<User> user = userRepository.findById(userId);
        if(user.isPresent()) {
            List<SetOfTasks> setOfTasksList = setOfTasksRepository.findAllByOwner(user.get());
            List<Task> allByOwner = taskRepository.findAllBySetOfTasksIn(setOfTasksList);
            if (allByOwner.isEmpty()) {
                // fill the gap with exception
                return null;
            } else
                return allByOwner;
        }
        return null;
    }

    // wyswietla wszystkie udostepnione dla mnie
    @GetMapping("allByMember")
    public List<Task> getAllByMember(@RequestParam Integer userId){
        Optional<User> user = userRepository.findById(userId);
        return user.map(value -> taskRepository.findAllByMembers(value)).orElse(null);
    }

    @PostMapping("save")
    public void saveTask(@RequestBody Task task){
        //TODO moze zmienic aby nie podawać date czy title
        taskRepository.save(task);
    }



    @PostMapping("changeCollection")
    public void changeSetOfTasks(@RequestBody Map<String,String> body){
        Optional<Task> task = taskRepository.findById(Integer.valueOf(body.get("taskId")));
        Optional<SetOfTasks> setOfTask = setOfTasksRepository.findById(Integer.valueOf(body.get("collectionId")));

        if(task.isPresent() && setOfTask.isPresent()) {
            task.get().setSetOfTasks(setOfTask.get());
            taskRepository.save(task.get());
        }
    }

    @PostMapping("addTag")
    public void addTag(@RequestBody Map<String,String> body){
        Optional<Task> task = taskRepository.findById(Integer.valueOf(body.get("taskId")));
        Optional<Tag> tag = tagRepository.findById(Integer.valueOf(body.get("tagId")));

        if(task.isPresent() && tag.isPresent()) {
            List<Tag> tags = task.get().getTags();
            tags.add(tag.get());
            task.get().setTags(tags);

            taskRepository.save(task.get());
        }
    }


    @PostMapping("removeTag")
    public void removeTag(@RequestBody Map<String,String> body){
        Optional<Task> task = taskRepository.findById(Integer.valueOf(body.get("taskId")));
        Optional<Tag> tag = tagRepository.findById(Integer.valueOf(body.get("tagId")));

        if(task.isPresent() && tag.isPresent()) {
            List<Tag> tags = task.get().getTags();
            tags.remove(tag.get());
            task.get().setTags(tags);

            taskRepository.save(task.get());
        }
    }

    @PostMapping("changeDescription")
    public void changeDescription(@RequestBody Map<String,String> body){
        Optional<Task> task = taskRepository.findById(Integer.valueOf(body.get("taskId")));

        if(task.isPresent()) {
            task.get().setDescription(body.get("desc"));
            taskRepository.save(task.get());
        }
    }

    @PostMapping("changeTitle")
    public void changeTitle(@RequestBody Map<String,String> body){
        Optional<Task> task = taskRepository.findById(Integer.valueOf(body.get("taskId")));

        if(task.isPresent()) {
            task.get().setTitle(body.get("title"));
            taskRepository.save(task.get());
        }
    }

    @PostMapping("changeDueDate")
    public void changeDueDate(@RequestBody Map<String,String> body){
        Optional<Task> task = taskRepository.findById(Integer.valueOf(body.get("taskId")));

        if(task.isPresent()) {
            task.get().setDueDate(body.get("dueDate"));
            taskRepository.save(task.get());
        }
    }

    @PostMapping("addMember")
    public void addMember(@RequestBody Map<String,String> body){
        Optional<Task> task = taskRepository.findById(Integer.valueOf(body.get("taskId")));
        Optional<User> member = userRepository.findById(Integer.valueOf(body.get("memberId")));

        if(task.isPresent() && member.isPresent()) {
            List<User> members = task.get().getMembers();
            members.add(member.get());
            task.get().setMembers(members);

            taskRepository.save(task.get());
        }
    }

    @PostMapping("removeMember")
    public void removeMember(@RequestBody Map<String,String> body){
        Optional<Task> task = taskRepository.findById(Integer.valueOf(body.get("taskId")));
        Optional<User> member = userRepository.findById(Integer.valueOf(body.get("memberId")));

        if(task.isPresent() && member.isPresent()) {
            List<User> members = task.get().getMembers();
            members.remove(member.get());
            task.get().setMembers(members);

            taskRepository.save(task.get());
        }
    }

    @PostMapping("addDependentTask")
    public void addDependentTask(@RequestBody Map<String,String> body){
        Optional<Task> task = taskRepository.findById(Integer.valueOf(body.get("taskId")));
        Optional<Task> dependent = taskRepository.findById(Integer.valueOf(body.get("dependentId")));

        if(task.isPresent() && dependent.isPresent()) {
            task.get().setOverridingTask(dependent.get());
            taskRepository.save(task.get());
        }
    }


    @PostMapping("complete")
    public boolean toggleComplete(@RequestParam Integer taskId){
        //TODO zwracać jakiegos booleana ?
        Optional<Task> task = taskRepository.findById(taskId);
        if(task.isPresent()) {
            Task task1 = task.get();
            if (task1.isComplete()) {
                task1.setComplete(false);
            } else {
                task1.setComplete(true);
            }
            taskRepository.save(task1);
            return task1.isComplete();
        }
        //TODO może tu coś zmienić, bo w przypadku błędu bedzie sie mieszać
        return false;
    }

    @PostMapping("deleteDependentTasks")
    public void deleteDependentTasks(@RequestParam Integer taskId){
        Optional<Task> task = taskRepository.findById(taskId);

        if(task.isPresent()) {
            task.get().setOverridingTask(null);
            taskRepository.save(task.get());
        }
    }

    @DeleteMapping("delete")
    public void deleteTask(@RequestParam Integer taskId){
        Optional<Task> task = taskRepository.findById(taskId);

        if(task.isPresent()) {
            List<Task> allByOverridingTask = taskRepository.findAllByOverridingTask(task.get());
            for (Task overriding : allByOverridingTask) {
                overriding.setOverridingTask(null);
            }
        }

        taskRepository.deleteById(taskId);
    }

    //TODO usunac
    @GetMapping("findAllByTitle")
    public List<Integer> findAllByTitle(@RequestParam String title){
        List<Task> allByTitle = taskRepository.findAllByTitle(title);
        List<Integer> integers = new ArrayList<>();
        for (Task task :
                allByTitle) {
            integers.add(task.getId());
        }
        return integers;
    }
}
