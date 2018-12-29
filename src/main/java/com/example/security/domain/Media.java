package com.example.security.domain;

/**
 * @author lixiao
 * entity 用户媒体
 */

public class Media {

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
}
