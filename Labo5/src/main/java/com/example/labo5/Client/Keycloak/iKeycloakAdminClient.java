package com.example.labo5.Client.Keycloak;

import com.example.labo5.Config.Keycloak.KeycloakFeignInterceptorConfig;
import com.example.labo5.Domain.DTO.KeycloakRoleDTO;
import feign.Response;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@FeignClient(name = "feign-admin", url = "${keycloak.server-url}", configuration = KeycloakFeignInterceptorConfig.class)
public interface iKeycloakAdminClient {
    //Metodo post realizado al realm que hemos creado al apartado de users que nos devolvera una Response de Feign (Asegurar que la importacion de Response venga de import feign.Response)
    @PostMapping("/admin/realms/${keycloak.realm}/users")
    Response createUser(@RequestBody Map<String, Object> user);

    // Obtener todos los roles del realm
    @GetMapping("/admin/realms/{realm}/roles")
    List<KeycloakRoleDTO> getRealmRoles(@PathVariable("realm") String realm, @RequestHeader("Authorization") String token);

    // Asignar roles a un usuario
    @PostMapping("/admin/realms/{realm}/users/{userId}/role-mappings/realm")
    void assignRealmRolesToUser(@PathVariable("realm") String realm,
                                @PathVariable("userId") String userId,
                                @RequestBody List<KeycloakRoleDTO> roles,
                                @RequestHeader("Authorization") String token);

    // Remover roles de un usuario
    @DeleteMapping("/admin/realms/{realm}/users/{userId}/role-mappings/realm")
    void removeRealmRolesFromUser(@PathVariable("realm") String realm,
                                  @PathVariable("userId") String userId,
                                  @RequestBody List<KeycloakRoleDTO> roles,
                                  @RequestHeader("Authorization") String token);


}
