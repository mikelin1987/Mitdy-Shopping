package com.mitdy.core.dto;

import java.io.Serializable;

public class DeleteDTO implements Serializable {

    private static final long serialVersionUID = -8775521639784721071L;

    private Long id;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

}
