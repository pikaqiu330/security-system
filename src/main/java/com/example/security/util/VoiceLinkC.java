package com.example.security.util;

import org.springframework.stereotype.Component;

@Component
public class VoiceLinkC {

    //���ض�̬��
    static {
        System.loadLibrary("CMdetectJNI");
    }
    public native void getVoice(String txtUrl, String voiceUrl);

}
