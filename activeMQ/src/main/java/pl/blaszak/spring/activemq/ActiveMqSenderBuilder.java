package pl.blaszak.spring.activemq;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.activemq.broker.BrokerService;

import javax.jms.*;

/**
 * Created by SG0945943 on 2017-04-03.
 */
public class ActiveMqSenderBuilder {

    private int ackMode = Session.AUTO_ACKNOWLEDGE;
    private String messageQueueName;
    private String messageBrokerUrl;
    private boolean transacted = false;
    private int deliveryMode = DeliveryMode.NON_PERSISTENT;
    private ActiveMqListener listener;

    public ActiveMqSenderBuilder withAckMode(int ackMode) {
        this.ackMode = ackMode;
        return this;
    }

    public ActiveMqSenderBuilder withMessageQueueName(String messageQueueName) {
        this.messageQueueName = messageQueueName;
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
            BrokerService broker = new BrokerService();
            broker.setPersistent(false);
            broker.setUseJmx(false);
            broker.addConnector(messageBrokerUrl);
            broker.start();
            ActiveMQConnectionFactory connectionFactory = new ActiveMQConnectionFactory(messageBrokerUrl);
            Connection connection = connectionFactory.createConnection();
            connection.start();
            Session session = connection.createSession(transacted, ackMode);
            Destination destination = session.createQueue(messageQueueName);
            MessageProducer producer = session.createProducer(destination);
            producer.setDeliveryMode(deliveryMode);
            MessageConsumer consumer = session.createConsumer(destination);
            consumer.setMessageListener(listener);
            return new ActiveMqSender(producer, session);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

    }
}
