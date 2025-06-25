package com.example.labo5.Domain.DTO;


import lombok.Data;

@Data
public class LoginRequest {
    private String username;
    private String password;
}
