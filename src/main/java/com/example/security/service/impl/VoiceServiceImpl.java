package com.example.security.service.impl;

import com.example.security.service.VoiceService;
import com.example.security.util.VoiceLinkC;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class VoiceServiceImpl implements VoiceService {

    @Autowired
    private VoiceLinkC voiceLinkC;

    @Override
    public void checkAnomalyTolinkJni(String txtPath,String voiceDirPath) {

        voiceLinkC.getVoice(txtPath,voiceDirPath);

    }
}
