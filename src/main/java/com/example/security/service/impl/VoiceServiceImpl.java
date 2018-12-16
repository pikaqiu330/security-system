package com.example.security.service.impl;

import com.example.security.service.VoiceService;
import com.example.security.util.VoiceLinkJNI;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class VoiceServiceImpl implements VoiceService {

    @Autowired
    private VoiceLinkJNI voiceLinkJNI;

    @Override
    public void checkAnomalyToLinkJni(String txtPath,String voiceDirPath) {
        System.out.println("run start");
        voiceLinkJNI.startVoiceJNI(txtPath,voiceDirPath);
        System.out.println("run stop");
    }

}
