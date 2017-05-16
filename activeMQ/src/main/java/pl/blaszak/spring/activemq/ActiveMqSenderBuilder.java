package pl.blaszak.spring.activemq;

import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.Connection;
import javax.jms.DeliveryMode;
import javax.jms.Destination;
import javax.jms.MessageConsumer;
import javax.jms.MessageProducer;
import javax.jms.Session;

/**
 * Created by SG0945943 on 2017-04-03.
 */
public class ActiveMqSenderBuilder {

    private int ackMode = Session.AUTO_ACKNOWLEDGE;
    private String topicName;
    private String messageBrokerUrl;
    private boolean transacted = false;
    private final int deliveryMode = DeliveryMode.NON_PERSISTENT;
    private ActiveMqListener listener;

    public ActiveMqSenderBuilder withAckMode(int ackMode) {
        this.ackMode = ackMode;
        return this;
    }

    public ActiveMqSenderBuilder withTopicName(String sendingQueueName) {
        this.topicName = sendingQueueName;
        return this;
    }

    public ActiveMqSenderBuilder withMessageBrokerUrl(String messageBrokerUrl) {
        this.messageBrokerUrl = messageBrokerUrl;
        return this;
    }

    public ActiveMqSenderBuilder withTransacted(boolean transacted) {
        this.transacted = transacted;
        return this;
    }

    public ActiveMqSenderBuilder withListener(ActiveMqListener listener) {
        this.listener = listener;
        return this;
    }

    public ActiveMqSender build() {

        try {
            ActiveMQConnectionFactory connectionFactory = new ActiveMQConnectionFactory(messageBrokerUrl);
            Connection connection = connectionFactory.createConnection();
            connection.start();
            Session session = connection.createSession(transacted, ackMode);
            Destination sendingDestination = session.createTopic(topicName);
            MessageProducer producer = session.createProducer(sendingDestination);
            producer.setDeliveryMode(deliveryMode);
            // Destination receivingDestination = session.createQueue(receivingQueueName);
            MessageConsumer consumer = session.createConsumer(sendingDestination);
            consumer.setMessageListener(listener);
            return new ActiveMqSender(producer, session);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

    }
}
