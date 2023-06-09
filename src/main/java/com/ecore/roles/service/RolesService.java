package com.ecore.roles.service;

import com.ecore.roles.model.Role;

import java.util.List;
import java.util.Map;
import java.util.UUID;

public interface RolesService {

    Role createRole(Role role);

    Role getRole(UUID id);

    List<Role> getRoles();

    List<Role> getRolesByUserIdAndTeamId(List<Map<String, String>> payload);

    Role getRoleByUserIdAndTeamId(UUID userId, UUID teamId);
}
