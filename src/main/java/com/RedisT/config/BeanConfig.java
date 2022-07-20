package com.RedisT.config;


import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.activemq.RedeliveryPolicy;
import org.apache.activemq.command.ActiveMQQueue;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jms.annotation.EnableJms;
import org.springframework.jms.config.DefaultJmsListenerContainerFactory;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.web.socket.server.standard.ServerEndpointExporter;

import javax.jms.DeliveryMode;
import javax.jms.Queue;
import javax.jms.Session;

/**
 * @Desc 消费者和生产者的配置类差不多,生产者不用配置websocket去掉就好,其他都是一样的
 */
//@Configuration
//@EnableJms
public class BeanConfig {
    @Value("${myQueue}")
    private String myQueue;

    /**
     * websocket配置Bean
     *
     * @return
     */
    @Bean
    public ServerEndpointExporter serverEndpointExporter() {
        return new ServerEndpointExporter();
    }

    /**
     * 定义存放消息的队列
     * @return
     */
    @Bean
    public Queue queue() {
        return new ActiveMQQueue(myQueue);
    }

    @Bean
    public RedeliveryPolicy redeliveryPolicy() {
        RedeliveryPolicy redeliveryPolicy = new RedeliveryPolicy();
        //是否在每次尝试重新发送失败后,增长这个等待时间
        redeliveryPolicy.setUseExponentialBackOff(true);
        //重发次数,默认为6次   这里设置为10次
        redeliveryPolicy.setMaximumRedeliveries(10);
        //重发时间间隔,默认为1秒
        redeliveryPolicy.setInitialRedeliveryDelay(1);
        //第一次失败后重新发送之前等待500毫秒,第二次失败再等待500 * 2毫秒,这里的2就是value
        redeliveryPolicy.setBackOffMultiplier(2);
        //是否避免消息碰撞
        redeliveryPolicy.setUseCollisionAvoidance(false);
        //设置重发最大拖延时间10s   只有UseExponentialBackOff(true)为true时才表示没有拖延时间
        redeliveryPolicy.setMaximumRedeliveryDelay(10000L);
        return redeliveryPolicy;
    }

    @Bean
    public ActiveMQConnectionFactory activeMQConnectionFactory(RedeliveryPolicy redeliveryPolicy) {
        ActiveMQConnectionFactory activeMQConnectionFactory =
                new ActiveMQConnectionFactory();
        activeMQConnectionFactory.setRedeliveryPolicy(redeliveryPolicy);
        return activeMQConnectionFactory;
    }

    @Bean
    public JmsTemplate jmsTemplate(ActiveMQConnectionFactory activeMQConnectionFactory, Queue queue) {
        JmsTemplate jmsTemplate = new JmsTemplate();
        jmsTemplate.setDeliveryMode(DeliveryMode.PERSISTENT);//进行持久化配置  NON_PERSISTENT - 表示非持久化，PERSISTENT - 表示持久化
        jmsTemplate.setConnectionFactory(activeMQConnectionFactory);
        jmsTemplate.setDefaultDestination(queue); //此处可不设置默认，在发送消息时也可设置队列
        jmsTemplate.setSessionAcknowledgeMode(Session.CLIENT_ACKNOWLEDGE);//客户端签收模式
        //  CLIENT_ACKNOWLEDGE -- 客户确认        DUPS_OK_ACKNOWLEDGE --  自动批量确认
        //  SESSION_TRANSACTED -- 事务提交并确认   AUTO_ACKNOWLEDGE -- 自动签收
        return jmsTemplate;
    }


    /**
     * 定义一个消息监听器连接工厂，这里定义的是点对点模式的监听器连接工厂
     *
     * @param activeMQConnectionFactory
     * @return
     */
    @Bean(name = "jmsQueueListener")
    public DefaultJmsListenerContainerFactory jmsQueueListenerContainerFactory(ActiveMQConnectionFactory activeMQConnectionFactory) {
        DefaultJmsListenerContainerFactory factory =
                new DefaultJmsListenerContainerFactory();
        factory.setConnectionFactory(activeMQConnectionFactory);
        //设置连接数
        factory.setConcurrency("1-10");
        //重连间隔时间
        factory.setRecoveryInterval(10000L);
        factory.setSessionAcknowledgeMode(4);
        return factory;
    }
}
