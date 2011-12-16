import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by IntelliJ IDEA.
 * User: davek
 * Date: 12/13/11
 * Time: 1:45 PM
 * To change this template use File | Settings | File Templates.
 */
public class ConsumerRunner implements Runnable {
    private final MessageConsumer consumer;
    private final Logger logger = LoggerFactory.getLogger(getClass());
    private final int numberOfMessages;

    public ConsumerRunner(MessageConsumer consumer, int numberOfMessages) {
        this.consumer = consumer;
        this.numberOfMessages = numberOfMessages;
    }

    public void run() {
        logger.debug("Getting {} messages from {}", numberOfMessages, consumer);
        long startTime;
        long stopTime;
        
        for (int i=1; i <= numberOfMessages; i++) {
            startTime = System.nanoTime();
            String message = consumer.getMessage();
            stopTime = System.nanoTime();
            logger.info("Message {} retrieved in {} ns", message, stopTime-startTime);
        }
        
        consumer.close();
    }
}
