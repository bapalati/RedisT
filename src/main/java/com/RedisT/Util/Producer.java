package com.RedisT.Util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsMessagingTemplate;
import org.springframework.stereotype.Component;

import javax.jms.Topic;

@Component
public class Producer {
    @Autowired
    JmsMessagingTemplate jmsMessagingTemplate;
//    @Autowired
//    Topic topic;
//
//    public void sendMsg(String msg){
//        jmsMessagingTemplate.convertAndSend(topic,msg);
//    }
}
