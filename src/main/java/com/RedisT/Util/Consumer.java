package com.RedisT.Util;

import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

@Component
public class Consumer {
    @JmsListener(destination = "test1", containerFactory="queueListener")
    public void list(String msg){
        System.out.println(msg+"get");
    }


}
