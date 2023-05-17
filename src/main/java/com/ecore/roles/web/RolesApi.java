package com.ecore.roles.web;

import com.ecore.roles.web.dto.RoleDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;
import java.util.UUID;

public interface RolesApi {

    ResponseEntity<RoleDto> createRole(
            RoleDto role);

    ResponseEntity<List<RoleDto>> getRoles();

    ResponseEntity<RoleDto> getRole(
            UUID roleId);

    @GetMapping(
            path = "/search",
            produces = {"application/json"})
    ResponseEntity<RoleDto> getRoleByUserAndTeam(
            @PathVariable UUID teamMemberId,
            @PathVariable UUID teamId);
}
