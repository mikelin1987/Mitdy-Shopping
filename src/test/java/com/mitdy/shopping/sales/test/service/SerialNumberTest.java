package com.mitdy.shopping.sales.test.service;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.mitdy.shopping.sales.service.SerialNumberGenerator;
import com.mitdy.shopping.test.base.BaseTest;

public class SerialNumberTest extends BaseTest {
    
    @Autowired
    private SerialNumberGenerator serialNumberGenerator;
    
    @Test
    public void test1() {
        String nextSerialNumber = serialNumberGenerator.getNextSerialNumber();
        logger.info("nextSerialNumber: {}", nextSerialNumber);
    }

}
