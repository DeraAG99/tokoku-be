package com.dera.tokokube.model;

import java.io.Serializable;
import java.time.LocalDateTime;

import org.springframework.stereotype.Component;

import lombok.Data;

@Component
@Data
public class ExceptionResponse implements Serializable {

    private int status;
    private String error;
    private String message;
    private LocalDateTime timestamp;

    // Buat constructor kosong untuk menghindari kesalahan injeksi dependensi
    public ExceptionResponse() {
    }

    public ExceptionResponse(int status, String error, String message) {
        this.status = status;
        this.error = error;
        this.message = message;
        this.timestamp = LocalDateTime.now();
    }
}
