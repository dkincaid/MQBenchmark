import com.rabbitmq.client.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.URISyntaxException;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;

/**
 * Created by IntelliJ IDEA.
 * User: davek
 * Date: 12/13/11
 * Time: 12:57 PM
 * To change this template use File | Settings | File Templates.
 */
public class RabbitMQMessageConsumer implements MessageConsumer {
    private final String uri;
    private final String queueName;
    private final Logger logger = LoggerFactory.getLogger(getClass());

    private Channel channel;
    private Connection connection;

    public RabbitMQMessageConsumer(String uri, String queueName) {
        this.uri = uri;
        this.queueName = queueName;

        try {
            ConnectionFactory factory = new ConnectionFactory();
            factory.setUri(uri);
            connection = factory.newConnection();
            channel = connection.createChannel();
            String exchangeName = "vet2pet";
            channel.exchangeDeclare(exchangeName, "direct", true);
            channel.queueDeclare(queueName, true, false, false, null);
            String routingKey = "v2pRouting";
            channel.queueBind(queueName, exchangeName, routingKey);
        } catch (IOException e) {
            logger.error(e.getMessage());
            e.printStackTrace();
        } catch (URISyntaxException e) {
            logger.error(e.getMessage());
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            logger.error(e.getMessage());
            e.printStackTrace();
        } catch (KeyManagementException e) {
            logger.error(e.getMessage());
            e.printStackTrace();
        }
    }

    public String getMessage() {
        logger.debug("Getting messsage from {}", toString());

        String message = "NO MESSAGE RECEIVED";
        boolean autoAck = false;
        GetResponse response;
        try {
            response = channel.basicGet(queueName, autoAck);

            if (response == null) {
                // No message retrieved.
            } else {
//                AMQP.BasicProperties props = response.getProps();
                byte[] body = response.getBody();
                message = Arrays.toString(body);
                long deliveryTag = response.getEnvelope().getDeliveryTag();
                channel.basicAck(deliveryTag, false);
            } 
        } catch (IOException e) {
            logger.error(e.getMessage());
            e.printStackTrace();
        }

    return message;
}

    public String toString() {
        return "RabbitMQ host: " + uri + " queue: " + queueName;
    }

    public void close() {
        try {
            channel.close();
            connection.close();
        } catch (IOException e) {
            logger.error(e.getMessage());
            e.printStackTrace();
        }
    }
}
