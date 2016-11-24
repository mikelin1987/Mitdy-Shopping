package com.mitdy.core.value;

import java.io.Serializable;

public class CommonCriteria implements Serializable {

    private static final long serialVersionUID = -1034287053905652074L;

    private String keyword;
    private Integer first;
    private Integer pageSize;

    public String getKeyword() {
        return keyword;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }

    public Integer getFirst() {
        return first;
    }

    public void setFirst(Integer first) {
        this.first = first;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

}
