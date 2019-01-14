package com.example.security.service.impl;

import com.example.security.controller.MediaController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import javax.websocket.*;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@ServerEndpoint(value = "/websocket/{ip}/{name}")
@Component
public class WebSocketServerImpl {
    private static final Logger LOG = LoggerFactory.getLogger(MediaController.class);

    public static Map<String,WebSocketServerImpl> map = new ConcurrentHashMap<>();

    //与某个客户端的连接会话，需要通过它来给客户端发送数据
    private Session session;

    /**
     * 连接建立成功调用的方法*/
    @OnOpen
    public void onOpen(@PathParam("ip") String ip,Session session) {
        this.session = session;
        map.put(ip,this);
        //webSocketSet.add(this);     //加入set中
        LOG.info("有新连接加入");
        sendMessage("连接成功");
    }

    /**
     * 连接关闭调用的方法
     */
    @OnClose
    public void onClose(@PathParam("ip") String ip) {
        map.remove(ip);
        //webSocketSet.remove(this);  //从set中删除
        LOG.info("有一连接关闭");
    }

    /**
     * 收到客户端消息后调用的方法
     *
     * @param message 客户端发送过来的消息*/
    @OnMessage
    public void onMessage(String message, Session session) {
        LOG.info("来自客户端的消息:" + message);

        //群发消息
        sendMassInfo(message);

        /*for (WebSocketServerImpl item : webSocketSet) {
            try {
                item.sendMessage(message);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }*/
    }

    /**
     *
     * @param session
     * @param error
     */
    @OnError
    public void onError(Session session, Throwable error) {
        LOG.error("发生错误");
        error.printStackTrace();
    }

    /**
     * 群发自定义消息
     * */
    public void sendInfo(String message){
        LOG.info(message);
        sendMassInfo(message);
        /*for (WebSocketServerImpl item : webSocketSet) {
            try {
                item.sendMessage(message);
            } catch (IOException e) {
                continue;
            }
        }*/
    }

    public void sendMessage(String message) {
        try {
            this.session.getBasicRemote().sendText(message);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void sendMassInfo(String message){
        for (String in: map.keySet()
        ) {
            WebSocketServerImpl socketServer = map.get(in);
            if(socketServer != null) {
                socketServer.sendMessage(message);
            }
        }
    }
}
