package com.RedisT.Util;


import lombok.Data;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

import javax.jms.JMSException;
import javax.jms.TextMessage;
import javax.websocket.*;
import javax.websocket.server.ServerEndpoint;
import java.util.concurrent.CopyOnWriteArraySet;
import javax.websocket.Session;

/**
 * @Desc 消费者类
 */
@ServerEndpoint("/websocket")
//@Component
@Data
public class Queue_Customer {
    /**
     * 每个客户端都会有相应的session,服务端可以发送相关消息
     */
    private Session session;

    /**
     * J.U.C包下线程安全的类，主要用来存放每个客户端对应的webSocket连接
     */
    private static CopyOnWriteArraySet<Queue_Customer> copyOnWriteArraySet = new CopyOnWriteArraySet<>();

    @OnOpen
    public void onOpen(Session session) {
        this.session = session;
        copyOnWriteArraySet.add(this);
    }

    @OnClose
    public void onClose() {
        copyOnWriteArraySet.remove(this);
    }

    @OnMessage
    public void onMessage(String message) {
    }

    @OnError
    public void onError(Session session, Throwable error) {
        error.printStackTrace();
    }


    @JmsListener(destination = "${myQueue}", containerFactory = "jmsQueueListener") //监听
    public void receive(TextMessage textMessage, javax.jms.Session session) throws JMSException {
        //遍历客户端
        for (Queue_Customer webSocket : copyOnWriteArraySet) {
            try {
                //服务器主动推送
                webSocket.session.getBasicRemote().sendText(textMessage.getText());
                textMessage.acknowledge();// 使用手动签收模式，需要手动的调用，如果不在catch中调用session.recover()消息只会在重启服务后重发
                System.out.println("websocket-1号消费者" + textMessage.getText());
            } catch (Exception e) {
                session.recover();// 此不可省略 重发信息使用
            }
        }
    }
}
