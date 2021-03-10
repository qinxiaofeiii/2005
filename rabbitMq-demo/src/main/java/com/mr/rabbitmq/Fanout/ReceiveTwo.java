package com.mr.rabbitmq.Fanout;

import com.mr.rabbitmq.utils.RabbitmqConnectionUtil;
import com.rabbitmq.client.*;

import java.io.IOException;

public class ReceiveTwo {

    //交换机名称
    private final static String EXCHANGE_NAME = "fanout_exchange_test";

    //队列名称
    private final static String QUEUE_NAME = "fanout_exchange_queue_2";

    public static void main(String[] args) throws Exception {
        // 获取连接
        Connection connection = RabbitmqConnectionUtil.getConnection();
        // 创建通道
        Channel channel = connection.createChannel();
        // 声明队列
        channel.queueDeclare(QUEUE_NAME, false, false, false, null);
        //消息队列绑定到交换机
        channel.queueBind(QUEUE_NAME,EXCHANGE_NAME,"");

        // 定义队列 接收端==》消费者
        DefaultConsumer consumer = new DefaultConsumer(channel) {
            // 监听队列中的消息，如果有消息，进行处理
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties,
                                       byte[] body) throws IOException {
                // body： 消息中参数信息
                String msg = new String(body);
                System.out.println(" [消费者-2] 收到消息 : " + msg );
            }
        };
        channel.basicConsume(QUEUE_NAME, true, consumer);

    }
}
