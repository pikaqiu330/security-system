package com.example.security.service.impl;

import com.example.security.domain.Media;
import com.example.security.service.MediaService;
import org.springframework.stereotype.Service;

@Service
public class MediaServiceImpl implements MediaService {

    @Override
    public Media Detection(Boolean b_jni,Media media) {
        if (b_jni){
            if (media.getIsAbnormity() == 1){
                media.setCount(0);
                media.setRefresh(0);
            }else {
                media.setIsAbnormity(1);
                media.setRefresh(1);
            }
            /*if (media.getCount() == 0)
                media.setRefresh(1);
            else
                media.setRefresh(0);*/
            media.setIsAbnormity(1);    //出现异常音
            media.setStatus(1);
            media.setType(0);
        }else {
            if (media.getIsAbnormity() == 1){   //前面出现异常音
                media.setCount(media.getCount() + 1);
                if (media.getCount() == 5){     //连续出现5个正常音刷回正常状态
                    media.setRefresh(1);
                    media.setIsAbnormity(0);
                    media.setCount(0);  //累计正常音数量归零
                }else {
                    media.setRefresh(0);
                }
            }else{
                media.setRefresh(0);
            }
            media.setStatus(0);
            media.setType(0);
        }
        return media;
    }
}
