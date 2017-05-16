package pl.blaszak.spring;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import pl.blaszak.spring.activemq.ActiveMqSender;
import pl.blaszak.spring.activemq.ActiveMqSenderBuilder;

/**
 * Created by SG0945943 on 2017-04-04.
 */
@SpringBootApplication
public class PongApplication {

    @Bean
    public PingListener getPingListener() {
        String appName = "PongApplication";
        return new PingListener(appName);
    }

    @Bean
    public ActiveMqSender getPongSender(PingListener pongListener) {
        ActiveMqSender sender = new ActiveMqSenderBuilder()
                .withMessageBrokerUrl("tcp://localhost:61616")
                .withTopicName("pingPongTopic")
                .withListener(pongListener)
                .build();
        pongListener.setSender(sender);
        return sender;
    }

    public static void main(String[] args) {
        SpringApplication.run(PongApplication.class, args);
        while(true);
    }
}
