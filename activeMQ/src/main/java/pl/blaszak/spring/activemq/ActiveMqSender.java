package pl.blaszak.spring.activemq;

import pl.blaszak.spring.activemq.exception.ActiveMqException;
import pl.blaszak.spring.activemq.model.PingPongEvent;

import javax.jms.JMSException;
import javax.jms.MessageProducer;
import javax.jms.ObjectMessage;
import javax.jms.Session;

/**
 * Created by SG0945943 on 2017-04-03.
 */
public class ActiveMqSender {

    private static final String MESSAGE_ERROR = "Can not send the event: ";
    private final MessageProducer producer;
    private final Session session;

    ActiveMqSender(MessageProducer producer, Session session) {
        this.producer = producer;
        this.session = session;
    }

    public void sendMessage(PingPongEvent event) {
        try {
            ObjectMessage objectMessage = session.createObjectMessage();
            objectMessage.setObject(event);
            producer.send(objectMessage);
        } catch (JMSException e) {
            throw new ActiveMqException(MESSAGE_ERROR + event.getEventId());
        }
    }
}
