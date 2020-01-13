package com.taskmanagementsystem.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.*;
import javax.validation.constraints.Email;
import java.util.List;

@Entity
public class User {

    @GeneratedValue
    @Id
    private Integer id;

    @Email
    @Column(nullable = false)
    private String email;

    @Column(nullable = false)
    private String username;

    private String firstName;
    private String lastName;

    @Lob
    private String photo;

    @Column(nullable = false)
    @JsonIgnore
    private String password;

    // default set is Inbox
    // after create user need append default set
    // @OneToMany(fetch= FetchType.LAZY, mappedBy = "User", cascade = CascadeType.ALL)
    // private List<SetOfTasks> setOfTasks = new ArrayList<>();

    @ManyToMany
    @JsonIgnore
    private List<Task> sharedTasks;

    public User() {}

    public User(String email, String username, String firstName, String lastName, String password) {
        this.email = email;
        this.username = username;
        this.firstName = firstName;
        this.lastName = lastName;
        this.password = password;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    @JsonIgnore
    public String getPassword() {
        return password;
    }

    @JsonProperty("password")
    public void setPassword(String password) {
        this.password = password;
    }

    public List<Task> getSharedTasks() {
        return sharedTasks;
    }

    public void setSharedTasks(List<Task> sharedTasks) {
        this.sharedTasks = sharedTasks;
    }
}
