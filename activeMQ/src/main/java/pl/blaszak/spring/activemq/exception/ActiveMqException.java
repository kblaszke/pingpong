package pl.blaszak.spring.activemq.exception;

/**
 * Created by SG0945943 on 2017-04-03.
 */
public class ActiveMqException extends RuntimeException {

    public ActiveMqException(Exception e) {
        super(e);
    }

    public ActiveMqException(String message) {
        super(message);
    }
}
