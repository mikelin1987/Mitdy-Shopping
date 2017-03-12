package com.mitdy.shopping.sales.mapper;

import java.util.Date;

import com.mitdy.shopping.sales.dto.SerialNumberDTO;

public interface SerialNumberMapper {

    public SerialNumberDTO querySerialNumberByDate(Date date);
    
    public void saveSerialNumber(SerialNumberDTO serialNumberDTO);
    
    public int updateSerialNumber(SerialNumberDTO serialNumberDTO);
    
    public int updateCurrentSerialNumber(SerialNumberDTO serialNumberDTO);
    
}
