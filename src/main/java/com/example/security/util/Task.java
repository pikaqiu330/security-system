package com.example.security.util;
import com.example.security.domain.Constant;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.io.File;

@Component
public class Task {
    private static final Logger LOG = LoggerFactory.getLogger(Task.class);

    @Scheduled(cron="0/30 * * * * ?")   //每5秒执行一次
    public void deleteVoiceLocal(){
        LOG.info("开始删除本端音频文件...");
        deleteVoice(Constant.FILEPATH_LOCAL);
        LOG.info("删除本端音频文件完成！");
        LOG.info("开始删除远端音频文件...");
        deleteVoice(Constant.FILEPATH_REMOTE);
        LOG.info("删除远端音频文件完成！");
    }

    public void deleteVoice(String path){
        File file = new File(path);
        File[] files = file.listFiles();
        if(files != null ){
            for(int i = 0;i < files.length - 5;i++){
                files[i].delete();
            }
        }
    }
}
