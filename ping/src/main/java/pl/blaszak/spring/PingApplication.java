package pl.blaszak.spring;

import org.springframework.context.annotation.Bean;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import pl.blaszak.spring.activemq.ActiveMqSender;
import pl.blaszak.spring.activemq.ActiveMqSenderBuilder;

/**
 * Created by SG0945943 on 2017-04-03.
 */
@SpringBootApplication
public class PingApplication {

    @Bean
    public PongListener getPongListener() {
        return new PongListener();
    }

    @Bean
    public ActiveMqSender getPingSender(PongListener pongListener) {
        ActiveMqSender sender = new ActiveMqSenderBuilder()
                .withMessageBrokerUrl("tcp://localhost:61616")
                .withMessageQueueName("pingpong")
                .withListener(pongListener)
                .build();
        pongListener.setSender(sender);
        return sender;
    }

    @Bean
    public PingPongStarter getPingPongStarter(ActiveMqSender pingSender) {
        return new PingPongStarter(pingSender);
    }

    public static void main(String[] args) {
        SpringApplication.run(PingApplication.class, args);
        while(true);
    }
}
