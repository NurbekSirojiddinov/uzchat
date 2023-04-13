package com.developers.uzchat.domain;

import jakarta.persistence.*;

import java.io.Serializable;
import java.time.Instant;
import java.util.List;

@Entity
public class Message implements Serializable {
    @Id
    private Long id;
    private Long conversationId;
    private Long senderId;
    @OneToMany(mappedBy = "message", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<MessageRecipient> recipientIds;

    @ManyToOne
    @JoinColumn(name = "conversation_id")
    private Conversation conversation;
    private String content;
    private Instant createdDate;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getConversationId() {
        return conversationId;
    }

    public void setConversationId(Long conversationId) {
        this.conversationId = conversationId;
    }

    public Long getSenderId() {
        return senderId;
    }

    public void setSenderId(Long senderId) {
        this.senderId = senderId;
    }

    public List<MessageRecipient> getRecipientIds() {
        return recipientIds;
    }

    public void setRecipientIds(List<MessageRecipient> recipientIds) {
        this.recipientIds = recipientIds;
    }

    public Conversation getConversation() {
        return conversation;
    }

    public void setConversation(Conversation conversation) {
        this.conversation = conversation;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Instant getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Instant createdDate) {
        this.createdDate = createdDate;
    }

    @Override
    public String toString() {
        return "Message{" +
                "id=" + id +
                ", conversationId=" + conversationId +
                ", senderId=" + senderId +
                ", recipientIds=" + recipientIds +
                ", conversation=" + conversation +
                ", content='" + content + '\'' +
                ", createdDate=" + createdDate +
                '}';
    }
}
