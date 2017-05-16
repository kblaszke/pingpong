package pl.blaszak.spring.activemq;

import org.apache.log4j.Logger;
import pl.blaszak.spring.activemq.exception.ActiveMqException;
import pl.blaszak.spring.activemq.model.PingPongEvent;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.ObjectMessage;
import java.util.concurrent.TimeUnit;

/**
 * Created by sg0945943 on 2017-03-31.
 */
public abstract class ActiveMqListener implements MessageListener {

    private static final long SLEEP_TIME = 2;
    protected static final int MAX_MESSAGES = 10;
    private static final Logger LOGGER = Logger.getLogger(ActiveMqListener.class);
    private final String ownerName;

    protected ActiveMqListener(String ownerName) {
        this.ownerName = ownerName;
    }

    protected abstract void onMessage(PingPongEvent event);

    public final void onMessage(Message message) {

        if (message instanceof ObjectMessage) {
            try {
                Object messageObject = ((ObjectMessage) message).getObject();
                PingPongEvent event = (PingPongEvent) messageObject;
                if (!ownerName.equals((event).getSender())) {
                    LOGGER.info(ownerName + " received event from: " + event.getSender() + " of id: " + event.getEventId() + " and count: " + event.getCounter());
                    onMessage(event);
                }
            } catch (JMSException e) {
                throw new ActiveMqException(e);
            } catch (ClassCastException ignored) {

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

    protected final void logSendEvent(PingPongEvent e) {
        LOGGER.info(ownerName + " sending event of id: " + e.getEventId() + " and count: " + e.getCounter());
    }

    protected String getOwnerName() {
        return ownerName;
    }
}
