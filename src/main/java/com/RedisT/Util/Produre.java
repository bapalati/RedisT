package com.RedisT.Util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsMessagingTemplate;
import org.springframework.stereotype.Component;

import javax.jms.Queue;

@Component
public class Produre {
    @Autowired
    JmsMessagingTemplate jmsMessagingTemplate;
    @Autowired
    Queue queue;
    public void sendMsg(String msg){
        System.out.println(msg + "send");
        jmsMessagingTemplate.convertAndSend(queue,msg);
    }
}
