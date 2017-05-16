package pl.blaszak.spring;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import pl.blaszak.spring.activemq.ActiveMqSender;
import pl.blaszak.spring.activemq.ActiveMqSenderBuilder;
import pl.blaszak.spring.activemq.model.PingPongEvent;

import java.util.Random;

/**
 * Created by SG0945943 on 2017-04-03.
 */
@SpringBootApplication
public class PingApplication {

    private final String appName = "PingApplication";

    @Bean
    public PongListener getPongListener() {
        return new PongListener(appName);
    }

    @Bean
    public ActiveMqSender getPingSender(PongListener pongListener) {
        ActiveMqSender sender = new ActiveMqSenderBuilder()
                .withMessageBrokerUrl("tcp://localhost:61616")
                .withTopicName("pingPongTopic")
                .withListener(pongListener)
                .build();
        pongListener.setSender(sender);
        sender.sendMessage(new PingPongEvent(appName, createRandomString(), 0));
        return sender;
    }

    public static void main(String[] args) {
        SpringApplication.run(PingApplication.class, args);
        while(true);
    }

    private String createRandomString() {
        Random random = new Random(System.currentTimeMillis());
        long randomLong = random.nextLong();
        return Long.toHexString(randomLong);
    }
}
