package com.dera.tokokube.model;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.springframework.stereotype.Component;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Data;

@Component
@Data
public class ExceptionResponse implements Serializable {

    private int code;
    private String status;
    private Object errors;
    private String timestamp;
    @JsonIgnore
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");


    // Buat constructor kosong untuk menghindari kesalahan injeksi dependensi
    public ExceptionResponse() {

    }

    public ExceptionResponse(int code, String status, Object errors) {

        this.code = code;
        this.status = status;
        this.errors = errors;
        this.timestamp = LocalDateTime.now().format(formatter);
    }
}
