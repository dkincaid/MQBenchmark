import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.sqs.AmazonSQSClient;
import com.amazonaws.services.sqs.model.DeleteMessageRequest;
import com.amazonaws.services.sqs.model.Message;
import com.amazonaws.services.sqs.model.ReceiveMessageRequest;
import com.amazonaws.services.sqs.model.ReceiveMessageResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by IntelliJ IDEA.
 * User: davek
 * Date: 12/13/11
 * Time: 1:03 PM
 * To change this template use File | Settings | File Templates.
 */
public class SQSMessageConsumer implements MessageConsumer {
    private String uri;
    private Logger logger = LoggerFactory.getLogger(getClass());
    private static final String AWS_ACCESS_KEY = "AKIAIFLSAH6FOUXXYR7Q";
    private static final String AWS_SECRET_KEY = "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";

    private ReceiveMessageRequest receiveMessageRequest;

    private AWSCredentials awsCredentials = new BasicAWSCredentials(AWS_ACCESS_KEY, AWS_SECRET_KEY);
    private AmazonSQSClient amazonSQSClient = new AmazonSQSClient(awsCredentials);
  

    public SQSMessageConsumer(String uri) {
        this.uri = uri;
        receiveMessageRequest = new ReceiveMessageRequest(uri).withMaxNumberOfMessages(1);
    }
    
    public String getMessage() {
        logger.debug("Getting message from {}", toString());
        ReceiveMessageResult messageResult = amazonSQSClient.receiveMessage(receiveMessageRequest);
        Message message = messageResult.getMessages().get(0);
        String messageBody = message.getBody();
        DeleteMessageRequest deleteMessageRequest = new DeleteMessageRequest(uri, message.getReceiptHandle());
        logger.debug("Received message {} from {}", messageBody, toString());
        return messageBody;
    }

    public String toString() {
        return "SQS uri: " + uri;
    }
}
