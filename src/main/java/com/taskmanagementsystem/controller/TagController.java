package com.taskmanagementsystem.controller;

import com.taskmanagementsystem.db.TagRepository;
import com.taskmanagementsystem.model.Tag;
import com.taskmanagementsystem.model.User;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("tags")
@CrossOrigin
public class TagController {
    private TagRepository tagRepository;

    public TagController(TagRepository tagRepository) {
        this.tagRepository = tagRepository;
    }

    @GetMapping("byUser")
    public List<Tag> getAllByUser(@RequestBody User user) {
        return tagRepository.findAllByUser(user);
    }

    @GetMapping("save")
    public void saveTag(@RequestBody Tag tag) {
        tagRepository.save(tag);
    }

    @DeleteMapping("delete")
    public void deleteTag(@RequestBody Tag tag) {
        tagRepository.delete(tag);
    }
}
