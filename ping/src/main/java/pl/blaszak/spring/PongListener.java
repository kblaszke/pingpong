package pl.blaszak.spring;

import org.apache.log4j.Logger;
import pl.blaszak.spring.activemq.ActiveMqListener;
import pl.blaszak.spring.activemq.ActiveMqSender;
import pl.blaszak.spring.pingpong.model.PingEvent;
import pl.blaszak.spring.pingpong.model.PingPongEvent;
import pl.blaszak.spring.pingpong.model.PongEvent;

import java.util.concurrent.TimeUnit;

/**
 * Created by SG0945943 on 2017-04-04.
 */
public class PongListener extends ActiveMqListener {

    private static Logger LOGGER = Logger.getLogger(PongListener.class);

    private ActiveMqSender sender;

    @Override
    public void onMessage(PingPongEvent event) {
        if (event instanceof PongEvent) {
            LOGGER.debug("Got PongEvent. Thinking...");
            sleep();
            if (event.getCounter() < MAX_MESSAGES) {
                PingEvent pingEvent = new PingEvent(event.getEventId(), event.getCounter() + 1);
                LOGGER.debug("Sending PingEvent...");
                sender.sendMessage(pingEvent);
            }
        }
    }

    public void setSender(ActiveMqSender sender) {
        this.sender = sender;
    }
}
