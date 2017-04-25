package pl.blaszak.spring.activemq;

import pl.blaszak.spring.activemq.exception.ActiveMqException;
import pl.blaszak.spring.pingpong.model.PingPongEvent;

import javax.jms.*;
import java.util.concurrent.TimeUnit;

/**
 * Created by sg0945943 on 2017-03-31.
 */
public abstract class ActiveMqListener implements MessageListener {

    private static final long SLEEP_TIME = 4;
    protected static final int MAX_MESSAGES = 10;

    public abstract void onMessage(PingPongEvent event);

    public final void onMessage(Message message) {

        if (message instanceof ObjectMessage) {
            try {
                Object messageObject = messageObject = ((ObjectMessage) message).getObject();
                if (messageObject instanceof PingPongEvent) {
                    onMessage((PingPongEvent) messageObject);
                }
            } catch (JMSException e) {
                throw new ActiveMqException(e);
            }
        }
    }

    protected void sleep() {
        try {
            TimeUnit.SECONDS.sleep(SLEEP_TIME);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
