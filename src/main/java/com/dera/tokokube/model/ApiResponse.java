package com.dera.tokokube.model;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.springframework.stereotype.Component;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Data;

@Component
@Data
public class ApiResponse implements Serializable {

    private int code;
    private String status;
    private Object data;
    private String timestamp;
    @JsonIgnore
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    public ApiResponse() {
        this.timestamp = LocalDateTime.now().format(formatter);;
    }

    public ApiResponse(int code, String status, Object data) {
        this.code = code;
        this.status = status;
        this.data = data;
        this.timestamp = LocalDateTime.now().format(formatter);;
    }
    
}
