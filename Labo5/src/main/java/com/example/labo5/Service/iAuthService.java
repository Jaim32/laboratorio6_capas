package com.example.labo5.Service;

import com.example.labo5.Domain.DTO.CreateUserDTO;
import com.example.labo5.Domain.DTO.KeycloakTokenResponse;

public interface iAuthService {

    KeycloakTokenResponse register(CreateUserDTO user) throws Exception;
    KeycloakTokenResponse login(String username, String password);

}