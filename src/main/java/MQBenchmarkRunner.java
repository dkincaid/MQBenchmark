/**
 * Created by IntelliJ IDEA.
 * User: davek
 * Date: 12/13/11
 * Time: 12:16 PM
 */
public class MQBenchmarkRunner {
    private static final String QUEUENAME = "data_testqueue";
    private static final String RABBITURI = "amqp://user:password@host";
    private static final String SQSURI = "https://queue.amazonaws.com/xxxxxxxxx/queuename";

    private static int numberOfMessages = 10;
    private static String systemToCheck = "both";
    
    public static void main(String[] argv) {
        if (argv.length == 2) {
            if (argv[0].equals("rabbitmq"))
                systemToCheck = "rabbitmq";
            else if (argv[0].equals("sqs"))
                systemToCheck = "sqs";
            else if (argv[0].equals("both"))
                systemToCheck = "both";
            else
                printUsage();

            numberOfMessages = Integer.parseInt(argv[1]);
        }
       

        if (systemToCheck.equals("rabbitmq") || systemToCheck.equals("both")) {
            startRabbitMQPublisher(numberOfMessages);
            //startRabbitMQConsumer(numberOfMessages);
        }

        if (systemToCheck.equals("sqs") || systemToCheck.equals("both")) {
            startSQSPublisher(numberOfMessages);
            startSQSConsumer(numberOfMessages);
        }
    }

    private static void startSQSPublisher(int numberOfMessages) {
        createAndStartPublisherThread(new SQSMessagePublisher(SQSURI), numberOfMessages);
    }

    private static void startRabbitMQPublisher(int numberOfMessages) {
        createAndStartPublisherThread(new RabbitMQMessagePublisher(RABBITURI, QUEUENAME), numberOfMessages);
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
        createAndStartConsumerThread(new RabbitMQMessageConsumer(RABBITURI, QUEUENAME), numberOfMessages);
    }
    private static void createAndStartConsumerThread(MessageConsumer messageConsumer, int numberOfMessages) {
        ConsumerRunner consumerRunner = new ConsumerRunner(messageConsumer, numberOfMessages);
        Thread consumerThread = new Thread(consumerRunner);
        consumerThread.start();
    }

    private static void printUsage() {
        System.out.println("Usage: MQBenchmarkRunner <systemTOCheck> <numberOfMessages>");
        System.out.println("");
        System.out.println("where <systemToCheck> is rabbitmq, sqs or both.");
        System.out.println("If no arguments are given the default is both and 10.");
        System.exit(0);
    }
}
