package com.example.security.util;
import com.example.security.config.LoadUserBean;
import com.example.security.domain.Media;
import com.example.security.service.impl.WebSocketServerImpl;
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

    @Scheduled(cron="0 0/5 * * * ?")   //每5分钟执行一次
    private void deleteVoiceFile(){
        LOG.info("开始清理服务器音频文件...");
        for (String in : LoadUserBean.map.keySet()) {
             //map.keySet()返回的是所有key的值
            Media media = LoadUserBean.map.get(in);//得到每个key多对用value的值
            uploadFIleUtil.deleteDirectory(media.getVoice().getRawPath());
            uploadFIleUtil.deleteDirectory(media.getVoice().getWavPath());
         }
        LOG.info("清理服务器音频文件完成！");
    }

    @Scheduled(cron="0/5 * * * * ?")   //每5秒钟执行一次
    private void videoNormalTask(){
        for (String in:LoadUserBean.map.keySet()
             ) {
            Media media = LoadUserBean.map.get(in);
            WebSocketServerImpl socketServer = WebSocketServerImpl.map.get(media.getUser().getIp());
            if(socketServer != null) {
                if (media.getNvms().equals(1)) {
                    taskProcessing(media,socketServer);
                } else {
                    for (String is : LoadUserBean.map.keySet()
                    ) {
                        Media isMedia = LoadUserBean.map.get(is);
                        if (!in.equals(is)) {
                            taskProcessing(isMedia,socketServer);
                        }
                    }
                }
            }
        }
    }

    private void taskProcessing(Media media,WebSocketServerImpl socketServer){
        if(media.getVideo().getStatus().equalsIgnoreCase("Warning")){
            if(media.getVideo().getIsAnomaly().equals(1)){
                long outTime = (System.currentTimeMillis() - media.getVideo().getTimestamp()) / 1000;
                if (outTime > 30) {
                    socketServer.sendMessage("Normal");
                    media.getVideo().setStatus("Normal");
                }
            }
        }
    }

}
