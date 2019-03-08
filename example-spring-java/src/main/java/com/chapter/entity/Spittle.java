package com.chapter.entity;

import lombok.Data;

import java.util.Date;

@Data
public class Spittle {

    private Long id;
    private String message;
    private Date time;
    private Double latitude;
    private Double longitude;

    public Spittle() {

    }

    public Spittle(Long id, String message, Date time) {
        this.id = id;
        this.message = message;
        this.time = time;
    }

}
