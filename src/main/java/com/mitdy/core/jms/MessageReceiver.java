package com.mitdy.core.jms;

import java.text.SimpleDateFormat;
import java.util.Date;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MessageReceiver implements MessageListener {

    public static Logger logger = LoggerFactory.getLogger(MessageReceiver.class);

    private SimpleDateFormat formater = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    public void onMessage(Message message) {
        if (message instanceof TextMessage) {
            TextMessage textMessage = (TextMessage) message;
            try {
                String receivedMessage = textMessage.getText();
                logger.info("Received message: {} at {}", receivedMessage, formater.format(new Date()));
            } catch (JMSException e) {
                e.printStackTrace();
                logger.error("onMessage error: {}", e.getMessage());
            }
        }
    }
}