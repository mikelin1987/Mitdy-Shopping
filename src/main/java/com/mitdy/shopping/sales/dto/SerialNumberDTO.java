package com.mitdy.shopping.sales.dto;

import java.io.Serializable;
import java.util.Date;

public class SerialNumberDTO implements Serializable {

    private static final long serialVersionUID = 4142260346517040093L;

    private String serialNumberId;
    private long currentSerialNumber;
    private long generatedSerialNumber;
    private Date serialNumberDate;

    public String getSerialNumberId() {
        return serialNumberId;
    }

    public void setSerialNumberId(String serialNumberId) {
        this.serialNumberId = serialNumberId;
    }

    public long getCurrentSerialNumber() {
        return currentSerialNumber;
    }

    public void setCurrentSerialNumber(long currentSerialNumber) {
        this.currentSerialNumber = currentSerialNumber;
    }

    public long getGeneratedSerialNumber() {
        return generatedSerialNumber;
    }

    public void setGeneratedSerialNumber(long generatedSerialNumber) {
        this.generatedSerialNumber = generatedSerialNumber;
    }

    public Date getSerialNumberDate() {
        return serialNumberDate;
    }

    public void setSerialNumberDate(Date serialNumberDate) {
        this.serialNumberDate = serialNumberDate;
    }

}
