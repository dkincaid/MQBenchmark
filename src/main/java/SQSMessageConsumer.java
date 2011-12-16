import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.sqs.AmazonSQSClient;
import com.amazonaws.services.sqs.model.DeleteMessageRequest;
import com.amazonaws.services.sqs.model.Message;
import com.amazonaws.services.sqs.model.ReceiveMessageRequest;
import com.amazonaws.services.sqs.model.ReceiveMessageResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: davek
 * Date: 12/13/11
 * Time: 1:03 PM
 */
public class SQSMessageConsumer implements MessageConsumer {
    private final String uri;
    private final Logger logger = LoggerFactory.getLogger(getClass());
    private static final String AWS_ACCESS_KEY = "put your key here";
    private static final String AWS_SECRET_KEY = "put your key here";

    private final ReceiveMessageRequest receiveMessageRequest;

    private final AWSCredentials awsCredentials = new BasicAWSCredentials(AWS_ACCESS_KEY, AWS_SECRET_KEY);
    private final AmazonSQSClient amazonSQSClient = new AmazonSQSClient(awsCredentials);
  

    public SQSMessageConsumer(String uri) {
        this.uri = uri;
        receiveMessageRequest = new ReceiveMessageRequest(uri).withMaxNumberOfMessages(1);
    }
    
    public String getMessage() {
        logger.debug("Getting message from {}", toString());
        int nummsgs = 0;
        String messageBody = "NO MESSAGE RECEIVED";

        while (nummsgs == 0) {
            ReceiveMessageResult messageResult = amazonSQSClient.receiveMessage(receiveMessageRequest);
            List<Message> messages = messageResult.getMessages();
            nummsgs = messages.size();
            for (Message message: messages) {
                messageBody = message.getBody();
                DeleteMessageRequest deleteMessageRequest = new DeleteMessageRequest(uri, message.getReceiptHandle());
                amazonSQSClient.deleteMessage(deleteMessageRequest);
                logger.debug("Received message {} from {}", messageBody, toString());
            }
        }

        return messageBody;
    }

    public String toString() {
        return "SQS uri: " + uri;
    }

    public void close() {

    }
}
