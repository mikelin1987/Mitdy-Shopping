package com.mitdy.shopping.test.service;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.mitdy.core.jms.MessageSender;
import com.mitdy.shopping.test.base.BaseTest;

public class ActiveMQTest extends BaseTest {

    @Autowired(required = false)
    private MessageSender messageSender;

    @Test
    public void sendMessageTest1() throws Exception {
        messageSender.sendSimpleMessage("Mike Lam");

        Thread.sleep(3000L);

    }

}
