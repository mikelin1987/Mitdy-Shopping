package com.mitdy.core.jms;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Session;
import javax.jms.TextMessage;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;
import org.springframework.stereotype.Component;

@Component
public class MessageSender {
    
    public static Logger logger = LoggerFactory.getLogger(MessageSender.class);

    @Autowired
    private JmsTemplate jmsTemplate;
    
    public void sendSimpleMessage(String messageText) {
        jmsTemplate.send(new MessageCreator() {
            
            @Override
            public Message createMessage(Session session) throws JMSException {
                TextMessage msg = session.createTextMessage("Hello, " + messageText);
                logger.info("the text message {}", msg.toString());
                return msg;
            }
        });
    }

}