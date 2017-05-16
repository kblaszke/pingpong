package pl.blaszak.spring.activemq.model;

import java.io.Serializable;

/**
 * Created by sg0945943 on 2017-03-31.
 */
public class PingPongEvent implements Serializable {

    private final String eventId;
    private final int counter;
    private final String sender;

    public PingPongEvent(String sender, String eventId, int counter) {
        this.sender = sender;
        this.eventId = eventId;
        this.counter = counter;
    }

    public String getEventId() {
        return eventId;
    }

    public int getCounter() {
        return counter;
    }

    public String getSender() {
        return sender;
    }
}
