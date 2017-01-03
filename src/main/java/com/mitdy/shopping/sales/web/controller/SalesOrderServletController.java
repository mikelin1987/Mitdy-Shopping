package com.mitdy.shopping.sales.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.mitdy.core.value.ResponseData;
import com.mitdy.shopping.sales.dto.CreateActivityOrderDTO;
import com.mitdy.shopping.sales.service.SalesOrderService;

@Controller
@RequestMapping("/salesOrder")
public class SalesOrderServletController {

    @Autowired
    private SalesOrderService salesOrderService;

    @RequestMapping(method = RequestMethod.POST, value = "/createActivityOrder")
    @ResponseBody
    public ResponseData<Boolean> createActivityOrder(@RequestBody CreateActivityOrderDTO orderDTO) {
        try {
            salesOrderService.createActivityOrder(orderDTO);
            return new ResponseData<Boolean>(true);
        } catch (Exception e) {
            System.out.println(e.getMessage());
//            return new ResponseData<Boolean>("Exception", e.getMessage());
            throw e;
        }
    }

}
