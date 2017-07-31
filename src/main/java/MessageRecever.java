import com.rabbitmq.client.*;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * Created by ctis-szx on 2017/7/15.
 */
public class MessageRecever {

    private static final String QUEUE_NAME = "testQueue1";
    private static final String HOST = "192.168.150.131";
    private static final Integer PORT = 5672;
    private static final String USER = "rabbitmq";
    private static final String PASSWORD = "rabbitmq";
    private static final String EXCHANGE = "songzhixin";
    private static final String ROUTING_KEY = "song.zhi.xin";
    private static final String ROUTING_KEY2 = "testRoutKey";


    public void receive() {
        try {
            ConnectionFactory cf = new ConnectionFactory();
            cf.setHost(HOST);
            cf.setPort(PORT);
            cf.setUsername(USER);
            cf.setPassword(PASSWORD);

            Connection cc = cf.newConnection();
            Channel channel = cc.createChannel();
            channel.queueDeclare(QUEUE_NAME, true, false, false, null);
            Consumer consumer = new DefaultConsumer(channel) {

                @Override
                public void handleDelivery(String consumerTag, Envelope envelope,
                                           AMQP.BasicProperties properties, byte[] body)
                        throws IOException {
                    String message = new String(body, "UTF-8");
                    System.out.println(" [x] Received '" + message + "'");
                }
            };
            channel.basicConsume(QUEUE_NAME, true, consumer);

        } catch (IOException e) {
            e.printStackTrace();
        } catch (TimeoutException e) {
            e.printStackTrace();
        }
    }

}
