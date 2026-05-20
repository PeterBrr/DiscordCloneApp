package com.example.clonediscordapp.data.model;

public class ChatMessage {
    private final String id;
    private final User sender;
    private final String content;
    private final String timestamp;

    public ChatMessage(String id, User sender, String content, String timestamp) {
        this.id = id;
        this.sender = sender;
        this.content = content;
        this.timestamp = timestamp;
    }

    public String getId() { return id; }
    public User getSender() { return sender; }
    public String getContent() { return content; }
    public String getTimestamp() { return timestamp; }
}
