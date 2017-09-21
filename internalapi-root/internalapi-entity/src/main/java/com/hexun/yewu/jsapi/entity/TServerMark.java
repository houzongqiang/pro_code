package com.hexun.yewu.jsapi.entity;

public class TServerMark {
    private String id;

    private String serverMark;

    private String serverUrl;

    private String serverName;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    public String getServerMark() {
        return serverMark;
    }

    public void setServerMark(String serverMark) {
        this.serverMark = serverMark == null ? null : serverMark.trim();
    }

    public String getServerUrl() {
        return serverUrl;
    }

    public void setServerUrl(String serverUrl) {
        this.serverUrl = serverUrl == null ? null : serverUrl.trim();
    }

    public String getServerName() {
        return serverName;
    }

    public void setServerName(String serverName) {
        this.serverName = serverName == null ? null : serverName.trim();
    }
}