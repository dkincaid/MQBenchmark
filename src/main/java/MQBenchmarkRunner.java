/**
 * Created by IntelliJ IDEA.
 * User: davek
 * Date: 12/13/11
 * Time: 12:16 PM
 */
public class MQBenchmarkRunner {
    public static final String QUEUENAME = "vet2pet-test";
    public static final String RABBITHOST = "localhost";
    public static final String SQSURI = "localhost";

    public static void main(String[] argv) {
        int numberOfMessages = 10;

        //startRabbitMQPublisher(numberOfMessages);
        //startRabbitMQConsumer(numberOfMessages);

        startSQSPublisher(numberOfMessages);
        startSQSConsumer(numberOfMessages);
    }

    private static void startSQSPublisher(int numberOfMessages) {
        createAndStartPublisherThread(new SQSMessagePublisher(RABBITHOST), numberOfMessages);
    }

    private static void startRabbitMQPublisher(int numberOfMessages) {
        createAndStartPublisherThread(new RabbitMQMessagePublisher(RABBITHOST, QUEUENAME), numberOfMessages);
    }

    private static void createAndStartPublisherThread(MessagePublisher messagePublisher, int numberOfMessages) {
        PublisherRunner sqsPublisherRunner = new PublisherRunner(messagePublisher, numberOfMessages);
        Thread sqsPublisherThread = new Thread(sqsPublisherRunner);
        sqsPublisherThread.start();
    }

    private static void startSQSConsumer(int numberOfMessages) {
        createAndStartConsumerThread(new SQSMessageConsumer(SQSURI), numberOfMessages);
    }
    private static void startRabbitMQConsumer(int numberOfMessages) {
        createAndStartConsumerThread(new RabbitMQMessageConsumer(RABBITHOST, QUEUENAME), numberOfMessages);
    }

    private static void createAndStartConsumerThread(MessageConsumer messageConsumer, int numberOfMessages) {
        ConsumerRunner consumerRunner = new ConsumerRunner(messageConsumer, numberOfMessages);
        Thread consumerThread = new Thread(consumerRunner);
        consumerThread.start();
    }
}
