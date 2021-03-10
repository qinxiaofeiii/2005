package com.mr.rabbitmq.work;

import com.mr.rabbitmq.utils.RabbitmqConnectionUtil;
import com.rabbitmq.client.*;

import java.io.IOException;

public class ReceiveOne {

    //队列名称
    private final static String QUEUE_NAME = "test_work_queue";

    public static void main(String[] args) throws Exception {
        //获取连接
        Connection connection = RabbitmqConnectionUtil.getConnection();
        //创建通道
        Channel channel = connection.createChannel();
        //声明队列
        channel.queueDeclare(QUEUE_NAME,false,false,false,null);
        //定义消费者
        DefaultConsumer consumer = new DefaultConsumer(channel){
            //监听队列中的消息,如果有消息,进行处理
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope,
                                       AMQP.BasicProperties properties, byte[] body) throws IOException {
                //body: 消息中的参数信息
                String msg = new String(body);
                System.err.println(" [消费者-1] 收到消息 : " + msg);
                //手动确认消息已经收到
                channel.basicAck(envelope.getDeliveryTag(),false);
            }
        };
        channel.basicConsume(QUEUE_NAME, false, consumer);

    }
}
