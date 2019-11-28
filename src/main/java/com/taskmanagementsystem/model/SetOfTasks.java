package com.taskmanagementsystem.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
public class SetOfTasks {

    @GeneratedValue
    @Id
    private Integer id;

    @Column(nullable = false)
    private String name;

    //TODO R ----->
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "owner", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JsonIgnore
    private User owner;

    //@OneToMany(fetch = FetchType.LAZY, mappedBy = "setOfTasks", cascade = CascadeType.ALL)
    //private List<Task> tasks;

    public SetOfTasks() {}

    public SetOfTasks(String name, User owner) {
        this.name = name;
        this.owner = owner;
    }


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public User getOwner() {
        return owner;
    }

    public void setOwner(User owner) {
        this.owner = owner;
    }
}
