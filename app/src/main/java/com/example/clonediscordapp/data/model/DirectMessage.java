package com.example.clonediscordapp.data.model;

public class DirectMessage {
    private final User user;
    private final String lastMessage;
    private final String timestamp;

    public DirectMessage(User user, String lastMessage, String timestamp) {
        this.user = user;
        this.lastMessage = lastMessage;
        this.timestamp = timestamp;
    }

    public User getUser() { return user; }
    public String getLastMessage() { return lastMessage; }
    public String getTimestamp() { return timestamp; }
}
