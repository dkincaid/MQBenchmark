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
    private String queuename;
    private Logger logger = LoggerFactory.getLogger(getClass());

    private Connection connection;
    private Channel channel;

    public RabbitMQMessagePublisher(String uri, String queuename) {
        this.uri = uri;
        this.queuename = queuename;

        try {
            ConnectionFactory factory = new ConnectionFactory();
            factory.setUri(uri);
            connection = factory.newConnection();
            channel = connection.createChannel();
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
       // TODO implement RabbitMQ putMessage
    }

     public String toString() {
        return "RabbitMQ host: " + uri + " queue: " + queuename;
    }
    
    public void close() {
        try {
            connection.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
