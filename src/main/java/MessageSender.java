import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * Created by ctis-szx on 2017/7/15.
 */
public class MessageSender {
    private static final String QUEUE_NAME = "testQueue1";
    private static final String HOST = "192.168.150.131";
    private static final Integer PORT = 5672;
    private static final String USER = "rabbitmq";
    private static final String PASSWORD = "rabbitmq";
    private static final String EXCHANGE = "songzhixin3";
    private static final String ROUTING_KEY = "song.zhi.xin";
    private static final String BINDING_KEY = "song.zhi.*";



    public void send(String msg){
        try {
            ConnectionFactory cf = new ConnectionFactory();
            cf.setHost(HOST);
            cf.setPort(PORT);
            cf.setUsername(USER);
            cf.setPassword(PASSWORD);

            Connection cc = cf.newConnection();
            Channel channel = cc.createChannel();
            channel.exchangeDeclare(EXCHANGE, "topic");
            channel.queueDeclare(QUEUE_NAME, true,false, false, null);
            channel.queueBind(QUEUE_NAME, EXCHANGE, BINDING_KEY);
            channel.basicPublish(EXCHANGE, ROUTING_KEY, null, msg.getBytes());

            channel.close();
            cc.close();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (TimeoutException e) {
            e.printStackTrace();
        }
    }
}
