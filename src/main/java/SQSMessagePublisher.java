import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.sqs.AmazonSQSClient;
import com.amazonaws.services.sqs.model.SendMessageRequest;
import com.amazonaws.services.sqs.model.SendMessageResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by IntelliJ IDEA.
 * User: davek
 * Date: 12/13/11
 * Time: 1:03 PM
 */
public class SQSMessagePublisher implements MessagePublisher {
    private final String uri;
    private final Logger logger = LoggerFactory.getLogger(getClass());
    private static final String AWS_ACCESS_KEY = "<put your key here";
    private static final String AWS_SECRET_KEY = "<put your key here";

    private final AWSCredentials awsCredentials = new BasicAWSCredentials(AWS_ACCESS_KEY, AWS_SECRET_KEY);
    private final AmazonSQSClient amazonSQSClient = new AmazonSQSClient(awsCredentials);

    public SQSMessagePublisher(String uri) {
        this.uri = uri;
    }
    
    public void putMessage(String message) {
        logger.debug("Sending message '{}' to {}", message, toString());
        SendMessageRequest sendMessageRequest = new SendMessageRequest(uri, message);
        SendMessageResult sendMessageResult = amazonSQSClient.sendMessage(sendMessageRequest);
        logger.debug("Message {} sent to {}", sendMessageResult.getMessageId(), toString());
    }

    public String toString() {
        return "SQS uri: " + uri;
    }

    public void close() {

    }
}
