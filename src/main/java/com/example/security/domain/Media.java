package com.example.security.domain;

/**
 * @author lixiao
 * entity 用户媒体
 */

public class Media {
    private Integer nvms;       //监控端：1本端/0远端

    private User user;

    private Voice voice;

    private Video video;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Voice getVoice() {
        return voice;
    }

    public void setVoice(Voice voice) {
        this.voice = voice;
    }

    public Video getVideo() {
        return video;
    }

    public void setVideo(Video video) {
        this.video = video;
    }

    public Integer getNvms() {
        return nvms;
    }

    public void setNvms(Integer nvms) {
        this.nvms = nvms;
    }

    @Override
    public String toString() {
        return "Media{" +
                "nvms=" + nvms +
                ", user=" + user +
                ", voice=" + voice +
                ", video=" + video +
                '}';
    }
}
