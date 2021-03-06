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
    private static final Logger logger = LoggerFactory.getLogger(Task.class);

    @Autowired
    private UploadFIleUtil uploadFIleUtil;

    @Scheduled(cron="0 0/5 * * * ?")   //每5分钟执行一次
    private void deleteVoiceFile(){
        logger.info("开始清理服务器音频文件...");
        for (String in : LoadUserBean.map.keySet()) {
            Media media = LoadUserBean.map.get(in);//得到每个key多对用value的值
            uploadFIleUtil.deleteDirectory(media.getVoice().getRawPath());
            uploadFIleUtil.deleteDirectory(media.getVoice().getWavPath());
         }
        logger.info("清理服务器音频文件完成！");
    }

    @Scheduled(cron="0/5 * * * * ?")   //每5秒钟执行一次
    private void videoNormalTask(){
        for (String in:LoadUserBean.map.keySet()
             ) {
            Media media = LoadUserBean.map.get(in);
            WebSocketServerImpl socketServer = WebSocketServerImpl.map.get(media.getUser().getIp());
            if(socketServer != null) {
                if (media.getNvms().equals(1)) {
                    if(media.getVideo().getIsAnomaly().equals(1)){
                        long outTime = (System.currentTimeMillis() - media.getVideo().getTimestamp()) / 1000;
                        if (outTime > 30) {
                            for (String remoteIp:LoadUserBean.map.keySet()
                            ) {
                                if(!remoteIp.equals(in)){
                                    Media mediaRemote = LoadUserBean.map.get(remoteIp);
                                    if(mediaRemote.getNvms().equals(0)){
                                        socketServer.sendInfo("Normal");
                                        logger.info("local and remote Normal...");
                                    }else {
                                        socketServer.sendMessage("Normal");
                                        logger.info("local Normal...");
                                    }
                                    media.getVideo().setStatus("Normal");
                                    media.getVideo().setIsAnomaly(0);
                                }
                            }
                        }
                    }

                } else {
                    for (String is : LoadUserBean.map.keySet()
                    ) {
                        if (!is.equals(in)) {
                            Media isMedia = LoadUserBean.map.get(is);
                            if(isMedia.getVideo().getIsAnomaly().equals(1)){
                                long outTime = (System.currentTimeMillis() - isMedia.getVideo().getTimestamp()) / 1000;
                                if (outTime > 30) {
                                    Media mediaRemote = LoadUserBean.map.get(is);
                                    if(mediaRemote.getNvms().equals(1)){
                                        socketServer.sendInfo("Normal");
                                        logger.info("local and remote Normal...");
                                    }else {
                                        socketServer.sendMessage("Normal");
                                        logger.info("remote Normal...");
                                    }
                                    media.getVideo().setStatus("Normal");
                                    media.getVideo().setIsAnomaly(0);
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}
