package com.example.security.util;
import com.example.security.config.LoadUserBean;
import com.example.security.domain.Media;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * @author lixiao
 * Task任务类
 */
@Component
public class Task {
    private static final Logger LOG = LoggerFactory.getLogger(Task.class);

    @Autowired
    private UploadFIleUtil uploadFIleUtil;

    @Scheduled(cron="0 0/5 * * * ?")   //每10分钟执行一次
    public void deleteVoiceLocal(){
        LOG.info("开始清理服务器音频文件...");
        for (String in : LoadUserBean.map.keySet()) {
             //map.keySet()返回的是所有key的值
            Media media = LoadUserBean.map.get(in);//得到每个key多对用value的值
            uploadFIleUtil.deleteDirectory(media.getVoice().getRawPath());
            uploadFIleUtil.deleteDirectory(media.getVoice().getWavPath());
         }
        LOG.info("清理服务器音频文件完成！");
    }

}
