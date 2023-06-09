package com.ecore.roles.web;

import com.ecore.roles.web.dto.RoleDto;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Map;
import java.util.UUID;

public interface RolesApi {

    ResponseEntity<RoleDto> createRole(
            RoleDto role);

    ResponseEntity<List<RoleDto>> getRoles();

    ResponseEntity<List<RoleDto>> getRolesByFilter(List<Map<String, String>> payload);

    ResponseEntity<RoleDto> getRole(
            UUID roleId);

    ResponseEntity<RoleDto> getRoleByUserIdAndTeamId(
            UUID teamMemberId,
            UUID teamId);
}
