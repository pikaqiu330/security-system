package com.example.security.service.impl;

import com.example.security.domain.Voice;
import com.example.security.service.VoiceService;
import org.springframework.stereotype.Service;

@Service
public class VoiceServiceImpl implements VoiceService {
    @Override
    public Voice getVoice(String voice_id) {
        Voice voice = new Voice();
        switch (voice_id){
            case "Fire":
                voice.setVoice_id("Fire");
                voice.setUrl("FireUrl");
                break;
            case "Plosive":
                voice.setVoice_id("Plosive");
                voice.setUrl("PlosiveUrl");
                break;
            case "Burglar":
                voice.setVoice_id("Burglar");
                voice.setUrl("BurglarUrl");
                break;
        }
        return voice;
    }
}
