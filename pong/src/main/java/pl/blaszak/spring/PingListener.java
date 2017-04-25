package pl.blaszak.spring;

import org.apache.log4j.Logger;
import pl.blaszak.spring.activemq.ActiveMqListener;
import pl.blaszak.spring.activemq.ActiveMqSender;
import pl.blaszak.spring.pingpong.model.PingEvent;
import pl.blaszak.spring.pingpong.model.PingPongEvent;
import pl.blaszak.spring.pingpong.model.PongEvent;

/**
 * Created by SG0945943 on 2017-04-04.
 */
public class PingListener extends ActiveMqListener {

    private static final Logger LOGGER = Logger.getLogger(PingListener.class);

    private ActiveMqSender sender;

    @Override
    public void onMessage(PingPongEvent event) {
        if (event instanceof PingEvent) {
            LOGGER.debug("Got PongEvent. Thinking...");
            sleep();
            if (event.getCounter() < MAX_MESSAGES) {
                PongEvent pongEvent = new PongEvent(event.getEventId(), event.getCounter() + 1);
                LOGGER.debug("Sending PingEvent...");
                sender.sendMessage(pongEvent);
            }
        }
    }

    public void setSender(ActiveMqSender sender) {
        this.sender = sender;
    }
}
