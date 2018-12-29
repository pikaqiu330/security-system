package com.example.security.domain;

/**
 * @author lixiao
 * entity 用户实体
 */
public class User {
    private String name;

    private String ip;

    private String videoLocalIp;

    private String videoRemoteIp;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getVideoLocalIp() {
        return videoLocalIp;
    }

    public void setVideoLocalIp(String videoLocalIp) {
        this.videoLocalIp = videoLocalIp;
    }

    public String getVideoRemoteIp() {
        return videoRemoteIp;
    }

    public void setVideoRemoteIp(String videoRemoteIp) {
        this.videoRemoteIp = videoRemoteIp;
    }

    @Override
    public String toString() {
        return "User{" +
                "name='" + name + '\'' +
                ", ip='" + ip + '\'' +
                ", videoLocalIp='" + videoLocalIp + '\'' +
                ", videoRemoteIp='" + videoRemoteIp + '\'' +
                '}';
    }
}
