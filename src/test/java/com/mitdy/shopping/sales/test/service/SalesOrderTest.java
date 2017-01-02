package com.mitdy.shopping.sales.test.service;

import java.io.IOException;

import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.mitdy.core.json.JacksonObjectMapper;
import com.mitdy.shopping.sales.dto.CreateActivityOrderDTO;
import com.mitdy.shopping.sales.service.SalesOrderService;
import com.mitdy.shopping.test.base.BaseTest;

public class SalesOrderTest extends BaseTest {
    
    @Autowired
    private SalesOrderService salesOrderService;
    
    @Test
    public void createActivityOrderTest() throws Exception {
        CreateActivityOrderDTO orderDTO = new CreateActivityOrderDTO();
        orderDTO.setActivityItemId(1L);
        orderDTO.setContactNo("13555639561");
        orderDTO.setMemberId(1L);
        orderDTO.setPayerName("林肥婷");
        orderDTO.setPaymentType("CASH");
        orderDTO.setQuantity(1);
        
        JacksonObjectMapper mapper = new JacksonObjectMapper();
        String text = mapper.writeValueAsString(orderDTO);
        
        System.out.println("text: " + text);
        
//        salesOrderService.createActivityOrder(orderDTO);
        
    }

}
