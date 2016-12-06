package com.mitdy.shopping.test.base;

import java.util.Date;

import org.junit.Before;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;

import com.mitdy.shopping.security.service.UserService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:spring/spring-service.xml",
        "classpath*:/spring/spring-persistence.xml" })
@TransactionConfiguration(transactionManager = "txMgr", defaultRollback = false)
public class BaseTest {

    protected Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    protected RedisTemplate<String, Object> redisTemplate;

    @Autowired
    protected UserService userService;

    @Before
    public void before() {
        logger.info("BaseTest.before at {}", new Date());
    }

}
