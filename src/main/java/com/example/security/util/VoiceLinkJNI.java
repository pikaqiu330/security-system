package com.example.security.util;

import org.springframework.stereotype.Component;

@Component
public class VoiceLinkJNI {

    //加载动态库
    /*static {
        System.loadLibrary("CMdetectJNI");
    }*/
    public native void startVoiceJNI(String txtUrl, String voiceUrl);

    public native void closeVoiceJNI();

}
