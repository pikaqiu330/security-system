package com.example.security;

import com.example.security.util.VoiceLinkJNI;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
@EnableAutoConfiguration
public class SecurityApplicationTests {
    @Autowired
    private  VoiceLinkJNI voiceLinkJNI;

    @Test
    public void contextLoads() {

    }

    /**
     * JNI测试
     */
    @Test
    public void testGetVoice(){
        String url = "E:/voice/VoiceRemote/A_3.raw";
        boolean b = voiceLinkJNI.AnomalyDetectionJNI(url);
        System.out.println(b);
    }

}
