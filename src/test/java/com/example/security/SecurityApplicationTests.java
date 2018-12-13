package com.example.security;

import com.example.security.service.VoiceService;
import com.example.security.util.VoiceLinkC;
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
    private VoiceService voiceService;

    @Test
    public void contextLoads() {

    }

    /**
     * JNI测试
     */
    @Test
    public void testGetVoice(){
        String txtUrl = "E:/wavfile/detect.txt";
        String voiceDirPath = "D:/XAMPP/htdocs/web_Record_func/upload/new_wav";
        voiceService.checkAnomalyToLinkJni(txtUrl,voiceDirPath);
    }

}
