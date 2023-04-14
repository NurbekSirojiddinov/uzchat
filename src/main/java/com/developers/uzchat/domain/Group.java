package com.developers.uzchat.domain;

import jakarta.persistence.*;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "\"group\"")
public class Group implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @ManyToOne()
    @JoinColumn(name = "owner_id")
    private User user;

    @ManyToMany
    @JoinTable(
            name = "group_members",
            joinColumns = {@JoinColumn(name = "group_id")},
            inverseJoinColumns = {@JoinColumn(name = "user_id")}
    )
    private List<User> members;

    @OneToMany(fetch = FetchType.LAZY)
    private List<Message> messages;

    public Group() {
        members = new ArrayList<>();
        messages = new ArrayList<>();
    }
    public void addMember(User member) {
        members.add(member);
    }

    public void addMessage(Message message) {
        messages.add(message);
    }

    public void removeMember(User member) {
        members.remove(member);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public List<User> getMembers() {
        return members;
    }

    public void setMembers(List<User> members) {
        this.members = members;
    }

    public List<Message> getMessages() {
        return messages;
    }

    public void setMessages(List<Message> messages) {
        this.messages = messages;
    }

    @Override
    public String toString() {
        return "Group{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", user=" + user +
                ", members=" + members +
                ", messages=" + messages +
                '}';
    }
}
