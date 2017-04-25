package pl.blaszak.spring;

import pl.blaszak.spring.activemq.ActiveMqSender;
import pl.blaszak.spring.pingpong.model.PingEvent;

import java.util.Random;

/**
 * Created by SG0945943 on 2017-04-04.
 */
public class PingPongStarter {

    public PingPongStarter(ActiveMqSender pingSender) {
        PingEvent pingEvent = new PingEvent(createRandomString(), 0);
        pingSender.sendMessage(pingEvent);
    }

    private String createRandomString() {
        Random random = new Random(System.currentTimeMillis());
        long randomLong = random.nextLong();
        return Long.toHexString(randomLong);
    }
}
