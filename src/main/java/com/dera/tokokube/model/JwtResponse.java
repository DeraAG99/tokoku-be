package com.dera.tokokube.model;

import java.io.Serializable;

import lombok.Data;

@Data
public class JwtResponse implements Serializable {
    private String type = "Bearer";
    private String username;
    private String email;
    private String role;
    private String token;
    private String refreshToken;

    // Response yang akan di dapat
    public JwtResponse(String accesToken,String refreshToken, String username, String email, String role) {
        this.username = username;
        this.email = email;
        this.role = role;
        this.token = accesToken;
        this.refreshToken = refreshToken;
    }
}
