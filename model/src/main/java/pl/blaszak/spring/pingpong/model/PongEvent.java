package pl.blaszak.spring.pingpong.model;

/**
 * Created by SG0945943 on 2017-04-04.
 */
public class PongEvent extends PingPongEvent {

    public PongEvent(String eventId, int counter) {
        super(eventId, counter);
    }
}
