package pl.blaszak.spring;

import pl.blaszak.spring.activemq.ActiveMqListener;
import pl.blaszak.spring.activemq.ActiveMqSender;
import pl.blaszak.spring.activemq.model.PingPongEvent;

/**
 * Created by SG0945943 on 2017-04-04.
 */
public class PingListener extends ActiveMqListener {

    private ActiveMqSender sender;

    public PingListener(String ownerName) {
        super(ownerName);
    }

    @Override
    public void onMessage(PingPongEvent event) {
        sleep();
        if (event.getCounter() < MAX_MESSAGES) {
            PingPongEvent pongEvent = new PingPongEvent(getOwnerName(),event.getEventId(), event.getCounter() + 1);
            logSendEvent(pongEvent);
            sender.sendMessage(pongEvent);
        }
    }

    public void setSender(ActiveMqSender sender) {
        this.sender = sender;
    }
}
