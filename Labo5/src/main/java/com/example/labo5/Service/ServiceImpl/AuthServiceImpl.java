package com.example.labo5.Service.ServiceImpl;

import com.example.labo5.Client.Keycloak.iKeycloakAdminClient;
import com.example.labo5.Client.Keycloak.iKeycloakAuthClient;
import com.example.labo5.Config.Keycloak.KeycloakProperties;
import com.example.labo5.Domain.DTO.CreateUserDTO;
import com.example.labo5.Domain.DTO.KeycloakRoleDTO;
import com.example.labo5.Domain.DTO.KeycloakTokenResponse;
import com.example.labo5.Service.iAuthService;
import feign.Response;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.util.List;

import static Utils.GeneralMappers.createUserDtoToMap;
import static Utils.GeneralMappers.loginToFormData;
import static Utils.UserIdFromKeycloak.getUserIdFromKeycloakResponse;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements iAuthService {

    private final iKeycloakAdminClient keycloakAdminClient;
    private final iKeycloakAuthClient keycloakAuthClient;
    private final KeycloakProperties keycloakProperties;

    @Override
    public KeycloakTokenResponse register(CreateUserDTO user) throws Exception {
        Response response = keycloakAdminClient.createUser(createUserDtoToMap(user));
        if (response.status() != 201) throw new Exception("Failed to create user: " + new String(response.body().asInputStream().readAllBytes(), StandardCharsets.UTF_8));
        String userId = getUserIdFromKeycloakResponse(response);
        return login(user.getUserName(), user.getPassword());
    }


    @Override
    public KeycloakTokenResponse login(String username, String password) {
        return keycloakAuthClient.getToken(loginToFormData(username, password, keycloakProperties.getClientId(), keycloakProperties.getClientSecret()));
    }

    private String getAdminToken() {
        return keycloakAuthClient.getToken(
                        loginToFormData(
                                "jaim32",           // tu usuario admin en Keycloak
                                "Asdf1234!",           // su contraseña
                                keycloakProperties.getClientId(),
                                keycloakProperties.getClientSecret()))
                .getAccessToken();
    }


    private void asignarRolPorDefecto(String userId) {
        String token = "Bearer " + getAdminToken();

        KeycloakRoleDTO defaultRole = new KeycloakRoleDTO();
        defaultRole.setName("user"); // Nombre del rol definido en tu realm
        defaultRole.setId(null); // Keycloak ignora el ID si no se proporciona

        keycloakAdminClient.assignRealmRolesToUser(
                keycloakProperties.getRealm(),
                userId,
                List.of(defaultRole),
                token
        );
    }

}