package com.mitdy.shopping.web.controller;

import java.io.Serializable;

public class ResponseResult<ResponseBody> implements Serializable {

    private static final long serialVersionUID = -8984797060061221230L;

    public static final String ERROR_CODE_INVALID_REQUEST = "N";
    public static final String ERROR_CODE_VALID_REQUEST = "Y";

    private boolean result;
    private ResponseBody body;
    private String errorCode;
    private String errorMessage;

    public ResponseResult() {
    }

    public ResponseResult(ResponseBody body) {
        this.result = true;
        this.body = body;
        errorCode = ERROR_CODE_VALID_REQUEST;
    }

    public ResponseResult(String errorCode, String errorMessage) {
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

    public ResponseBody getBody() {
        return body;
    }

    public void setBody(ResponseBody body) {
        this.body = body;
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
