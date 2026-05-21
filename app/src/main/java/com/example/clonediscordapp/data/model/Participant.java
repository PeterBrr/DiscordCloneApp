package com.example.clonediscordapp.data.model;

public class Participant {
    public int uid;
    public String name;
    public boolean isMuted;
    public boolean isVideoOff;
    public boolean isSpeaking;
    public boolean isSharingScreen;

    public Participant(int uid, String name) {
        this.uid = uid;
        this.name = name;
        this.isMuted = false;
        this.isVideoOff = false;
        this.isSpeaking = false;
        this.isSharingScreen = false;
    }
}
