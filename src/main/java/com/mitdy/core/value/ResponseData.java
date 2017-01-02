package com.mitdy.core.value;

import java.io.Serializable;

public class ResponseData<ResponseType> implements Serializable {

    private static final long serialVersionUID = -1078186834135478214L;

    private boolean result;
    private ResponseType type;
    private String errorCode;
    private String errorMessage;

    public ResponseData() {
    }

    public ResponseData(ResponseType type) {
        this.result = true;
        this.type = type;
    }

    public ResponseData(String errorCode, String errorMessage) {
        this.result = false;
        this.errorCode = errorCode;
        this.errorMessage = errorMessage;
    }

    public boolean isResult() {
        return result;
    }

    public void setResult(boolean result) {
        this.result = result;
    }

    public ResponseType getType() {
        return type;
    }

    public void setType(ResponseType type) {
        this.type = type;
    }

    public String getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

}
