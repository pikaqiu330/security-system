package com.example.security.util;

import org.springframework.stereotype.Component;

@Component
public class VoiceLinkC {

    //���ض�̬��
    static {
        System.loadLibrary("CMdetectJNI");
    }
    public native void startVoiceJNI(String txtUrl, String voiceUrl);

    public native void closeVoiceJNI();

}
