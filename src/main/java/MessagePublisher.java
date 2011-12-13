/**
 * Created by IntelliJ IDEA.
 * User: davek
 * Date: 12/13/11
 * Time: 12:51 PM
 * To change this template use File | Settings | File Templates.
 */
public interface MessagePublisher {
    public void putMessage(String message);
    public String toString();

    void close();
}
