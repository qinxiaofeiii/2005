package com.mr.rabbitmq.work;

import com.mr.rabbitmq.utils.RabbitmqConnectionUtil;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;

public class SendMessage {
    private final static String QUEUE_NAME = "test_work_queue";

    public static void main(String[] args) throws Exception {
        // 获取到连接
        Connection connection = RabbitmqConnectionUtil.getConnection();
        // 创建通道
        Channel channel = connection.createChannel();
        //声明队列
        channel.queueDeclare(QUEUE_NAME,false,false,false,null);
        //发送100条数据
        for (int i = 0; i < 100; i++) {
            String message = "我送快递的 ---> 你是大件货还是小件货" + i;

            channel.basicPublish("", QUEUE_NAME, null, message.getBytes());

            System.out.println("send" + message + "success");
        }
        // 关闭通道和连接
        channel.close();
        connection.close();
    }
}
