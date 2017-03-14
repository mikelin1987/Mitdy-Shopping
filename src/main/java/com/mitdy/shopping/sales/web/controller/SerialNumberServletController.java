package com.mitdy.shopping.sales.web.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.mitdy.core.value.ResponseData;
import com.mitdy.shopping.sales.service.impl.SerialNumberGenerator;

@Controller
@RequestMapping("/common")
public class SerialNumberServletController {

    protected Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private SerialNumberGenerator serialNumberGenerator;

    @RequestMapping(method = RequestMethod.GET, value = "/getNextSerialNumber")
    @ResponseBody
    public ResponseData<String> getNextSerialNumber() {
        try {
            return new ResponseData<String>(serialNumberGenerator.getNextSerialNumber());
        } catch (Exception e) {
            logger.info("SerialNumberServletController.getNextSerialNumber()->Exception: {}", e.getMessage());
            throw e;
        }
    }

}
