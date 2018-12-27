package com.example.security.service.impl;

import com.example.security.domain.Media;
import com.example.security.service.MediaService;
import org.springframework.stereotype.Service;

@Service
public class MediaServiceImpl implements MediaService {

    private boolean isRefresh = true;

    @Override
    public Media Detection(Boolean b_jni) {
        Media media = new Media();
        if (b_jni){
            media.setStatus(1);
            media.setType(0);
            media.setRefresh(0);
            if (isRefresh){
                media.setRefresh(1);
                isRefresh = false;
            }
        }else {
            media.setStatus(0);
            media.setType(0);
            media.setRefresh(0);
            if(!isRefresh){
                Media.count++;
                if(Media.count == 5){                //连续5个false刷新Refresh
                    Media.count = 0;
                    media.setRefresh(1);
                    isRefresh = true;
                }
            }
        }
        return media;
    }
}
