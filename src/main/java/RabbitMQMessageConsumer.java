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
public class RabbitMQMessageConsumer implements MessageConsumer {
    private String uri;
    private String queuename;
    private Logger logger = LoggerFactory.getLogger(getClass());

    private Connection connection;
    private Channel channel;

    public RabbitMQMessageConsumer(String uri, String queuename) {
        this.uri = uri;
        this.queuename = queuename;

        try {
            ConnectionFactory factory = new ConnectionFactory();
            factory.setUri(uri);
            connection = factory.newConnection();
            channel = connection.createChannel();
        } catch (IOException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        } catch (URISyntaxException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        } catch (KeyManagementException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
    }

    public String getMessage() {
        logger.debug("Getting messsage from {}", toString());
        // TODO implement RabbitMQ getMessage
        return null;
    }

    public String toString() {
        return "RabbitMQ host: " + uri + " queue: " + queuename;
    }
}
