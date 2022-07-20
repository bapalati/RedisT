package com.RedisT;

import com.RedisT.Util.Producer;
import com.RedisT.Util.Produre;
import com.RedisT.pojo.User;
import org.apache.activemq.ActiveMQConnectionFactory;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.BoundHashOperations;
import org.springframework.data.redis.core.BoundValueOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.jms.JmsException;
import org.springframework.jms.annotation.EnableJms;
import org.springframework.jms.core.JmsMessagingTemplate;

import javax.annotation.Resource;
import javax.jms.*;
import java.io.FileNotFoundException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

@SpringBootTest
class RedisTApplicationTests {

//    @Autowired
//    Producer producer;
    @Test
    void contextLoads() throws Exception {
//        producer.sendMsg("起飞？");
        RandomAccessFile file=new RandomAccessFile("C:\\Users\\night owl\\Desktop\\test.txt","rw");
        FileChannel channel = file.getChannel();

        ByteBuffer buffer = ByteBuffer.allocate(1024);

        int byteRead=channel.read(buffer);
        System.out.println(byteRead);
        while (byteRead!=-1){
            System.out.println("byteread="+byteRead);
            buffer.flip();
            while(buffer.hasRemaining()){
                System.out.println((char)buffer.get());
            }
            buffer.clear();
            byteRead=channel.read(buffer);
            System.out.println(byteRead);
        }

        file.close();

    }

}
