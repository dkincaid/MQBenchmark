import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.URISyntaxException;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;

/**
 * Created by IntelliJ IDEA.
 * User: davek
 * Date: 12/13/11
 * Time: 12:57 PM
 * To change this template use File | Settings | File Templates.
 */
public class RabbitMQMessagePublisher implements MessagePublisher {
    private String uri;
    private String queueName;
    private String exchangeName = "vet2pet";
    private String routingKey = "v2pRouting";

    private Logger logger = LoggerFactory.getLogger(getClass());

    private Connection connection;
    private Channel channel;

    public RabbitMQMessagePublisher(String uri, String queueName) {
        this.uri = uri;
        this.queueName = queueName;

        try {
            ConnectionFactory factory = new ConnectionFactory();
            factory.setUri(uri);
            connection = factory.newConnection();
            channel = connection.createChannel();
            channel.exchangeDeclare(exchangeName, "direct", true);
            channel.queueDeclare(queueName, true, false, false, null);
            channel.queueBind(queueName, exchangeName, routingKey);

        } catch (IOException e) {
            e.printStackTrace();
        } catch (URISyntaxException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (KeyManagementException e) {
            e.printStackTrace();
        }
    }

    public void putMessage(String message) {
        logger.debug("Sending message '{}' to {}", message, toString());
        byte[] messageBodyBytes = message.getBytes();
        try {
            channel.basicPublish(exchangeName, routingKey, null, messageBodyBytes);
        } catch (IOException e) {
            logger.error(e.getMessage());
            e.printStackTrace();
        }
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
