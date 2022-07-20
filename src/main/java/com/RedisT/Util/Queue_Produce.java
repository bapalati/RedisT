package com.RedisT.Util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.jms.Queue;
import java.util.UUID;

/**
 * @Desc 生产者
 */
@Component
public class Queue_Produce {
    //    @Autowired
//    private JmsMessagingTemplate jmsMessagingTemplate; //系列一
    @Autowired
    private Queue queue;
    @Autowired
    private JmsTemplate jmsTemplate;

    /**
     * 间隔10秒钟定投
     */
    @Scheduled(fixedDelay = 10000)
    public void produceMsgScheduled() {
        jmsTemplate.convertAndSend(queue, "$$$$$$$:" + UUID.randomUUID().toString().substring(0, 7));
        System.out.println("produceMsgScheduled send ok");
    }
}

