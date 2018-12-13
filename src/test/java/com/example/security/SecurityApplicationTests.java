package com.example.security;

import com.example.security.util.VoiceLinkC;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
@EnableAutoConfiguration
public class SecurityApplicationTests {

    @Test
    public void contextLoads() {

    }

    /**
     * JNI测试
     */
    @Test
    public void testGetVoice(){
        VoiceLinkC voiceLinkC = new VoiceLinkC();
        voiceLinkC.getVoice("E:/wavfile/detect.txt", "E:/wavfile");
    }

}
