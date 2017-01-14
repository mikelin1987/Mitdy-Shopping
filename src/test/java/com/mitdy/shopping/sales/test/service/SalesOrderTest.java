package com.mitdy.shopping.sales.test.service;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.mitdy.core.json.JacksonObjectMapper;
import com.mitdy.shopping.sales.dto.CreateActivityOrderDTO;
import com.mitdy.shopping.sales.service.SalesActivityService;
import com.mitdy.shopping.sales.service.SalesOrderService;
import com.mitdy.shopping.test.base.BaseTest;

public class SalesOrderTest extends BaseTest {
    
    @Autowired
    private SalesOrderService salesOrderService;
    
    @Autowired
    private SalesActivityService salesActivityService;
    
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
    
    @Test
    public void updateTest() {
        int count = salesActivityService.increaseActivityItemSellCount(1L, 1);
        System.out.println("count: " + count);
    }

}
