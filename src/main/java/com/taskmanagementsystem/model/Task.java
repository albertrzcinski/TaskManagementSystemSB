package com.taskmanagementsystem.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import java.util.Date;
import java.util.List;
import java.util.Set;

@Entity
public class Task {

    @GeneratedValue
    @Id
    private Integer id;

    @Column(nullable = false)
    private Date creationDate;

    @Column(nullable = false)
    private String title;

    private String dueDate;
    private String description;

    @Column(nullable = false)
    private boolean complete;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "setOfTasks", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    private SetOfTasks setOfTasks;

    @ManyToMany
    private List<User> members;

    @OneToOne
    private Task overridingTask;

    @ManyToMany
    @OnDelete(action = OnDeleteAction.CASCADE)
    private List<Tag> tags;

    public Task() {
    }

    public Task(Date creationDate, String title, boolean complete, SetOfTasks setOfTasks, String description) {
        this.creationDate = creationDate;
        this.title = title;
        this.complete = complete;
        this.setOfTasks = setOfTasks;
        this.description = description;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDueDate() {
        return dueDate;
    }

    public void setDueDate(String dueDate) {
        this.dueDate = dueDate;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean isComplete() {
        return complete;
    }

    public void setComplete(boolean complete) {
        this.complete = complete;
    }

    public SetOfTasks getSetOfTasks() {
        return setOfTasks;
    }

    public void setSetOfTasks(SetOfTasks setOfTasks) {
        this.setOfTasks = setOfTasks;
    }

    public List<User> getMembers() {
        return members;
    }

    public void setMembers(List<User> members) {
        this.members = members;
    }

    public Task getOverridingTask() {
        return overridingTask;
    }

    public void setOverridingTask(Task overridingTask) {
        this.overridingTask = overridingTask;
    }

    public List<Tag> getTags() {
        return tags;
    }

    public void setTags(List<Tag> tags) {
        this.tags = tags;
    }
}
