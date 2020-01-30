package com.taskmanagementsystem.controller;

import com.taskmanagementsystem.db.TagRepository;
import com.taskmanagementsystem.db.UserRepository;
import com.taskmanagementsystem.model.Tag;
import com.taskmanagementsystem.model.User;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("tags")
@CrossOrigin
public class TagController {
    private TagRepository tagRepository;
    private UserRepository userRepository;

    public TagController(TagRepository tagRepository, UserRepository userRepository) {
        this.tagRepository = tagRepository;
        this.userRepository = userRepository;
    }

    @GetMapping("byUser")
    public List<Tag> getAllByUser(@RequestParam Integer userId) {
        Optional<User> user = userRepository.findById(userId);
        return user.map(value -> tagRepository.findAllByUser(value)).orElse(null);
    }

    @PostMapping("save")
    public void saveTag(@RequestBody Tag tag) {
        tagRepository.save(tag);
    }

    @DeleteMapping("delete")
    public void deleteTag(@RequestParam Integer id) {
        tagRepository.deleteById(id);
    }
}
