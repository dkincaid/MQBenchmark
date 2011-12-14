import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by IntelliJ IDEA.
 * User: davek
 * Date: 12/13/11
 * Time: 1:45 PM
 * To change this template use File | Settings | File Templates.
 */
public class PublisherRunner implements Runnable {
    private final MessagePublisher publisher;
    private final int numberOfMessages;
    private final Logger logger = LoggerFactory.getLogger(getClass());

    public PublisherRunner(MessagePublisher publisher, int numberOfMessages) {
        this.publisher = publisher;
        this.numberOfMessages = numberOfMessages;
    }

    public void run() {
        logger.debug("Sending {} messages to {}", numberOfMessages, publisher);
        long startTime;
        long stopTime;

        for (int i=1; i <= numberOfMessages; i++) {
            startTime = System.nanoTime();
            publisher.putMessage("test message " + String.valueOf(i));
            stopTime = System.nanoTime();
            logger.info("Message {} queued in {} ns", String.valueOf(i), stopTime-startTime);
        }

        publisher.close();
    }
}
