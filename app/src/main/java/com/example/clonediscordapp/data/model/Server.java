package com.example.clonediscordapp.data.model;

import com.google.firebase.firestore.DocumentId;

public class Server {
    @DocumentId
    private String serverId;
    private String serverName;
    private String ownerId;
    private String iconUrl;
    private String purpose;
    private int orderIndex;
    private String accentColor;

    public Server() {
        this.serverId = "";
        this.serverName = "";
        this.ownerId = "";
        this.iconUrl = "";
        this.purpose = "";
        this.accentColor = "#7289DA";
    }

    public Server(String serverName, String ownerId, String iconUrl, String purpose, String accentColor) {
        this.serverName = serverName;
        this.ownerId = ownerId;
        this.iconUrl = iconUrl;
        this.purpose = purpose;
        this.accentColor = accentColor;
    }

    // Compatibility constructor for MockData
    public Server(String serverId, String serverName, String iconUrl) {
        this.serverId = serverId;
        this.serverName = serverName;
        this.iconUrl = iconUrl;
        this.ownerId = "";
        this.purpose = "";
        this.accentColor = "#7289DA";
    }

    // Getters and Setters
    public String getServerId() { return serverId; }
    public void setServerId(String serverId) { this.serverId = serverId; }

    public String getServerName() { return serverName; }
    public void setServerName(String serverName) { this.serverName = serverName; }

    public String getOwnerId() { return ownerId; }
    public void setOwnerId(String ownerId) { this.ownerId = ownerId; }

    public String getIconUrl() { return iconUrl; }
    public void setIconUrl(String iconUrl) { this.iconUrl = iconUrl; }

    public String getPurpose() { return purpose; }
    public void setPurpose(String purpose) { this.purpose = purpose; }

    public int getOrderIndex() { return orderIndex; }
    public void setOrderIndex(int orderIndex) { this.orderIndex = orderIndex; }

    public String getAccentColor() { return accentColor != null ? accentColor : "#7289DA"; }
    public void setAccentColor(String accentColor) { this.accentColor = accentColor; }

    // --- CÁC PHƯƠNG THỨC COMPATIBILITY ĐỂ KHÔNG LÀM HỎNG CODE GIAO DIỆN CŨ ---
    public String getId() { return serverId; }
    public void setId(String id) { this.serverId = id; }
    public String getName() { return serverName; }
    public String getImageUrl() { return iconUrl; }
}
