package com.mr.rabbitmq.simple;

import com.mr.rabbitmq.utils.RabbitmqConnectionUtil;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;

public class SendMessage {
    private final static String QUEUE_NAME = "simple_queue";

    public static void main(String[] args) throws Exception {
        //获取连接
        Connection connection = RabbitmqConnectionUtil.getConnection();
        //创建通道
        Channel channel = connection.createChannel();

        channel.queueDeclare(QUEUE_NAME,false,false,false,null);
        //发送的消息
        String msg = "我算命的,你算什么东西?";
        //发送消息
        channel.basicPublish("",QUEUE_NAME,null,msg.getBytes());
        System.err.println("消息 :" + msg + "-->发送到" +QUEUE_NAME+" success");
        //关闭通道和连接
        channel.close();
        connection.close();
    }

}
