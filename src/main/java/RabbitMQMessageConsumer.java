import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.GetResponse;
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
        GetResponse response = null;

        while (response == null) {
            try {
                response = channel.basicGet(queueName, autoAck);

                if (response == null) {
                    message = "NO MESSAGE RECEIVED";
                } else {
                    byte[] body = response.getBody();
                    message = new String(body);
                    long deliveryTag = response.getEnvelope().getDeliveryTag();
                    channel.basicAck(deliveryTag, false);
                }
            } catch (IOException e) {
                logger.error(e.getMessage());
                e.printStackTrace();
            }
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
