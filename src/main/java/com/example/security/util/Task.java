package com.example.security.util;
import com.example.security.domain.Constant;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class Task {
    private static final Logger LOG = LoggerFactory.getLogger(Task.class);

    @Autowired
    private UploadFIleUtil uploadFIleUtil;

    /*@Scheduled(cron="0 0/5 * * * ?")   //每5秒执行一次
    public void deleteVoiceLocal(){
        LOG.info("开始删除本端音频文件...");
        uploadFIleUtil.deleteDirectory(Constant.FILEPATH_LOCAL);
        LOG.info("删除本端音频文件完成！");
        LOG.info("开始删除远端音频文件...");
        uploadFIleUtil.deleteDirectory(Constant.FILEPATH_REMOTE);
        LOG.info("删除远端音频文件完成！");
    }*/

}
