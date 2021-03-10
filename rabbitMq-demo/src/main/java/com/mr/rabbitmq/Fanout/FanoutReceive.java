package com.mr.rabbitmq.Fanout;

import com.mr.rabbitmq.utils.RabbitmqConnectionUtil;
import com.rabbitmq.client.*;



public class FanoutReceive {
    //交换机名称
    private final static String EXCHANGE_NAME = "fanout_exchange_test";

    public static void main(String[] args) throws Exception {
        // 获取到连接
        Connection connection = RabbitmqConnectionUtil.getConnection();
        // 获取通道
        Channel channel = connection.createChannel();

        channel.exchangeDeclare(EXCHANGE_NAME,"fanout");

        //发送的消息内容
        String message = "good good study";

        channel.basicPublish(EXCHANGE_NAME, "", null, message.getBytes());

        System.out.println(" 消息发送 '" + message + "' 到交换机 success");
        // 关闭通道和连接
        channel.close();
        connection.close();
    }
}
