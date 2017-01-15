package com.mitdy.shopping.sales.test.service;

import java.io.Serializable;
import java.util.Map;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.mitdy.core.json.JacksonObjectMapper;
import com.mitdy.shopping.sales.dto.CreateActivityOrderDTO;
import com.mitdy.shopping.sales.dto.GoodsInfoDTO;
import com.mitdy.shopping.sales.mapper.SalesActivityItemMapper;
import com.mitdy.shopping.sales.service.SalesActivityService;
import com.mitdy.shopping.sales.service.SalesOrderService;
import com.mitdy.shopping.test.base.BaseTest;

public class SalesOrderTest extends BaseTest {
    
    @Autowired
    private SalesOrderService salesOrderService;
    
    @Autowired
    private SalesActivityService salesActivityService;
    
    @Autowired
    private SalesActivityItemMapper salesActivityItemMapper;
    
    @Test
    public void createActivityOrderByHibernateTest() throws Exception {
        CreateActivityOrderDTO orderDTO = createOrderDTO();
        
        JacksonObjectMapper mapper = new JacksonObjectMapper();
        String text = mapper.writeValueAsString(orderDTO);
        
        System.out.println("text: " + text);
        
        salesOrderService.createActivityOrderByHibernate(orderDTO);
    }

    @Test
    public void createActivityOrderByNativeSQLTest() throws Exception {
        CreateActivityOrderDTO orderDTO = createOrderDTO();
        
        JacksonObjectMapper mapper = new JacksonObjectMapper();
        String text = mapper.writeValueAsString(orderDTO);
        
        System.out.println("text: " + text);
        
        salesOrderService.createActivityOrderByNativeSQL(orderDTO);
    }
    
    @Test
    public void createActivityOrderByMyBatisTest() throws Exception {
        CreateActivityOrderDTO orderDTO = createOrderDTO();
        
        JacksonObjectMapper mapper = new JacksonObjectMapper();
        String text = mapper.writeValueAsString(orderDTO);
        
        System.out.println("text: " + text);
        
        salesOrderService.createActivityOrderByMyBatis(orderDTO);
    }
    
    private CreateActivityOrderDTO createOrderDTO() {
        CreateActivityOrderDTO orderDTO = new CreateActivityOrderDTO();
        orderDTO.setActivityItemId(1L);
        orderDTO.setContactNo("13555639561");
        orderDTO.setMemberId(1L);
        orderDTO.setPayerName("林肥婷");
        orderDTO.setPaymentType("CASH");
        orderDTO.setQuantity(1);
        return orderDTO;
    }
    
    @Test
    public void updateTest() {
        int count = salesActivityService.increaseActivityItemSellCount(1L, 1);
        System.out.println("count: " + count);
    }
    
    @Test
    public void findGoodsInfoTest() {
        
//        Map<String, Serializable> findGoodsInfoMapByItemId2 = salesActivityItemMapper.findGoodsInfoMapByItemId2(1L);
//        System.out.println("findGoodsInfoMapByItemId2: " + findGoodsInfoMapByItemId2);
        
//        Map<String, Object> findGoodsInfoMapByItemId = salesActivityItemMapper.findGoodsInfoMapByItemId(1L);
//        System.out.println("map: " + findGoodsInfoMapByItemId);
        
        for (int i = 0; i < 10; i++) {
            GoodsInfoDTO goodsInfoDTO = salesActivityService.findGoodsInfoMapByItemId(1L);
            System.out.println("goodsInfoDTO: " + goodsInfoDTO);
        }
    }

}
