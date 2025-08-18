package com.ecom.project.security.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.Getter;


@Data
public class LoginRequest {

    @NotBlank
    private String username;

    @NotBlank
    private String password;

//    public void setUsername(String username) {
//        this.username = username;
//    }
//
//    public void setPassword(String password) {
//        this.password = password;
//    }
}
