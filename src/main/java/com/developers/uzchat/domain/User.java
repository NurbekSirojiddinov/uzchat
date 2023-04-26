package com.developers.uzchat.domain;

import jakarta.persistence.*;

import java.io.Serializable;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

@Entity(name = "\"user\"")
public class User implements Serializable {
    private String bio;
    private String email;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String password;
    private Instant status; //last seen
    private String username;


    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    List<Group> groups;

    @ManyToMany(mappedBy = "members")
    List<Group> membershipGroups; //List of Groups that I'm member of

    @ManyToMany(mappedBy = "participants")
    List<Conversation> conversations;

    public User() {
        groups = new ArrayList<>();
        membershipGroups = new ArrayList<>();
        conversations = new ArrayList<>();
    }


    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public String getBio() {
        return bio;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Instant getStatus() {
        return status;
    }

    public void setStatus(Instant status) {
        this.status = status;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public List<Group> getGroups() {
        return groups;
    }

    public void setGroups(List<Group> groups) {
        this.groups = groups;
    }

    public List<Group> getMembershipGroups() {
        return membershipGroups;
    }

    public void setMembershipGroups(List<Group> membershipGroups) {
        this.membershipGroups = membershipGroups;
    }

    public List<Conversation> getConversations() {
        return conversations;
    }

    public void setConversations(List<Conversation> conversations) {
        this.conversations = conversations;
    }

    @Override
    public String toString() {
        return "User{" +
                "bio='" + bio + '\'' +
                ", id=" + id +
                ", name='" + name + '\'' +
                ", password='" + password + '\'' +
                ", status=" + status +
                ", username='" + username + '\'' +
                ", groups=" + groups +
                ", membershipGroups=" + membershipGroups +
                ", conversations=" + conversations +
                '}';
    }
}
