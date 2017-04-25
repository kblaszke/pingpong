package pl.blaszak.spring.pingpong.model;

import java.io.Serializable;

/**
 * Created by sg0945943 on 2017-03-31.
 */
public class PingPongEvent implements Serializable {

    private final String eventId;
    private final int counter;

    public PingPongEvent(String eventId, int counter) {
        this.eventId = eventId;
        this.counter = counter;
    }

    public String getEventId() {
        return eventId;
    }

    public int getCounter() {
        return counter;
    }
}
