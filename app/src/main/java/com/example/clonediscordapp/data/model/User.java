package com.example.clonediscordapp.data.model;

import java.util.Collections;
import java.util.List;

public class User {
    private String userId;      // ID duy nhất từ Firebase Auth
    private String username;    // Tên hiển thị
    private String email;       // Địa chỉ email
    private String profilePic;  // Link ảnh đại diện (URL)
    private String status;      // Trạng thái: "online" hoặc "offline"
    private String aboutMe;

    // Constructor trống bắt buộc phải có để Firebase mapping dữ liệu
    public User() {
        this.userId = "";
        this.username = "";
        this.email = "";
        this.profilePic = "";
        this.status = "offline";
        this.aboutMe = "";
    }

    // Constructor đầy đủ để tạo user mới khi đăng ký
    public User(String userId, String username, String email) {
        this.userId = userId;
        this.username = username;
        this.email = email;
        this.status = "online"; // Mặc định khi mới tạo là online
        this.profilePic = "";   // Để trống nếu chưa có ảnh
        this.aboutMe = "";
    }

    // Compatibility constructor for MockData
    public User(String userId, String username, String email, String profilePic, boolean online, String aboutMe, List<Role> roles) {
        this.userId = userId;
        this.username = username;
        this.email = email;
        this.profilePic = profilePic;
        this.status = online ? "online" : "offline";
        this.aboutMe = aboutMe;
    }

    // Getter và Setter cho Firebase
    public String getUserId() { return userId; }
    public void setUserId(String userId) { this.userId = userId; }

    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getProfilePic() { return profilePic; }
    public void setProfilePic(String profilePic) { this.profilePic = profilePic; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public String getAboutMe() { return aboutMe != null ? aboutMe : ""; }
    public void setAboutMe(String aboutMe) { this.aboutMe = aboutMe; }

    // --- CÁC PHƯƠNG THỨC COMPATIBILITY ĐỂ KHÔNG LÀM HỎNG CODE GIAO DIỆN CŨ ---
    public String getId() { return userId; }
    public String getName() { return username; }
    public String getAvatarUrl() { return profilePic; }
    public boolean isOnline() { return "online".equalsIgnoreCase(status); }
    public List<Role> getRoles() { return Collections.emptyList(); }

    public static class Role {
        private final String name;
        private final long color;

        public Role(String name, long color) {
            this.name = name;
            this.color = color;
        }

        public String getName() { return name; }
        public long getColor() { return color; }
    }
}
