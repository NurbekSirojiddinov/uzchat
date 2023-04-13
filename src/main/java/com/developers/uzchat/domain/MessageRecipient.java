package com.developers.uzchat.domain;

import jakarta.persistence.*;

import java.io.Serializable;

@Entity
public class MessageRecipient implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "messages_id")
    private Message message;

    @ManyToOne
    @JoinColumn(name = "recipient")
    private User user;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Message getMessage() {
        return message;
    }

    public void setMessage(Message message) {
        this.message = message;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
