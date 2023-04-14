package com.developers.uzchat.domain;

import jakarta.persistence.*;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Conversation implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @OneToMany(mappedBy = "conversation", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Message> messages;

    @ManyToMany
    @JoinTable(
            name = "conversation_participant",
            joinColumns = @JoinColumn(name = "conversation_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id"))
    private List<User> participants;

    private String name;

    @Enumerated(EnumType.STRING)
    private ConversationType type;

    private int unreadMessages;

    public Conversation() {
        messages = new ArrayList<>();
        participants = new ArrayList<>();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public List<Message> getMessages() {
        return messages;
    }

    public void setMessages(List<Message> messages) {
        this.messages = messages;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ConversationType getType() {
        return type;
    }

    public void setType(ConversationType type) {
        this.type = type;
    }

    public int getUnreadMessages() {
        return unreadMessages;
    }

    public void setUnreadMessages(int unreadMessages) {
        this.unreadMessages = unreadMessages;
    }

    @Override
    public String toString() {
        return "Conversation{" +
                "id=" + id +
                ", messages=" + messages +
                ", name='" + name + '\'' +
                ", type=" + type +
                ", unreadMessages=" + unreadMessages +
                '}';
    }
}
