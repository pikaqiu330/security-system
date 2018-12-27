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

    /**
     * JNI测试
     */
    @Test
    public void contextLoads() {

        boolean b = voiceLinkJNI.AnomalyDetectionJNI("E:/voice/voice_node1/A_2.wav");
        System.out.println(b);
    }

}
