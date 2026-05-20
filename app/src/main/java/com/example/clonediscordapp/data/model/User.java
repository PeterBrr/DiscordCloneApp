package com.example.clonediscordapp.data.model;

import java.util.Collections;
import java.util.List;

public class User {
    private final String id;
    private final String name;
    private final String username;
    private final String avatarUrl;
    private final boolean isOnline;
    private final String aboutMe;
    private final List<Role> roles;

    public User(String id, String name, String username, String avatarUrl,
                boolean isOnline, String aboutMe, List<Role> roles) {
        this.id = id;
        this.name = name;
        this.username = username;
        this.avatarUrl = avatarUrl;
        this.isOnline = isOnline;
        this.aboutMe = aboutMe;
        this.roles = roles != null ? roles : Collections.emptyList();
    }

    public String getId() { return id; }
    public String getName() { return name; }
    public String getUsername() { return username; }
    public String getAvatarUrl() { return avatarUrl; }
    public boolean isOnline() { return isOnline; }
    public String getAboutMe() { return aboutMe; }
    public List<Role> getRoles() { return roles; }

    public static class Role {
        private final String name;
        private final long color; // ARGB color as long (e.g. 0xFFED4245)

        public Role(String name, long color) {
            this.name = name;
            this.color = color;
        }

        public String getName() { return name; }
        public long getColor() { return color; }
    }
}
