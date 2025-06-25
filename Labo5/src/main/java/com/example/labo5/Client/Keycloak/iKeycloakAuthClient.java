package com.example.labo5.Client.Keycloak;

import com.example.labo5.Config.Keycloak.KeycloakAuthFeignConfig;
import com.example.labo5.Domain.DTO.KeycloakTokenResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "keycloak-service", url = "${keycloak.server-url}", configuration = KeycloakAuthFeignConfig.class)
public interface iKeycloakAuthClient {
    // Metodo de tipo post realizado a nuestro realm, mediante el protocolo openid-connect que obtendra un token y envia un Body de tipo MultiValueMap<String, String> con el nombre formData. Esta funcion devuelve un KeycloakTokenResponse, definido anteriormente
    @PostMapping(value = "/realms/${keycloak.realm}/protocol/openid-connect/token", consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    public KeycloakTokenResponse getToken(@RequestBody MultiValueMap<String, String> formData);

}