package com.ecore.roles.service.impl;

import com.ecore.roles.client.model.Team;
import com.ecore.roles.exception.ResourceExistsException;
import com.ecore.roles.exception.ResourceNotFoundException;
import com.ecore.roles.model.Membership;
import com.ecore.roles.model.Role;
import com.ecore.roles.repository.MembershipRepository;
import com.ecore.roles.repository.RoleRepository;
import com.ecore.roles.service.RolesService;
import lombok.NonNull;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.*;

@Log4j2
@Service
public class RolesServiceImpl implements RolesService {

    public static final String DEFAULT_ROLE = "Developer";

    private final RoleRepository roleRepository;
    private final MembershipRepository membershipRepository;

    @Autowired
    public RolesServiceImpl(
            RoleRepository roleRepository,
            MembershipRepository membershipRepository) {
        this.roleRepository = roleRepository;
        this.membershipRepository = membershipRepository;
    }

    @Override
    public Role createRole(@NonNull Role r) {
        if (roleRepository.findByName(r.getName()).isPresent()) {
            throw new ResourceExistsException(Role.class);
        }
        return roleRepository.save(r);
    }

    @Override
    public Role getRole(@NonNull UUID rid) {
        return roleRepository.findById(rid)
                .orElseThrow(() -> new ResourceNotFoundException(Role.class, rid));
    }

    @Override
    public List<Role> getRoles() {
        return roleRepository.findAll();
    }

    @Override
    public List<Role> getRolesByUserIdAndTeamId(@RequestBody List<Map<String, String>> payload) {
        List<Role> result = new ArrayList<>();
        for (Map<String, String> m : payload) {
            UUID userId = UUID.fromString(m.get("userId"));
            UUID teamId = UUID.fromString(m.get("teamId"));
            Optional<Membership> membership = membershipRepository.findByUserIdAndTeamId(userId, teamId);
            if (membership.isPresent()) {
                result.add(membership.get().getRole());
            }
        }
        return result;
    }

    @Override
    public Role getRoleByUserIdAndTeamId(UUID userId, UUID teamId) {
        Optional<Membership> membership = membershipRepository.findByUserIdAndTeamId(userId, teamId);
        if (membership.isEmpty()) {
            throw new ResourceNotFoundException(Team.class, teamId);
        }
        return membership.get().getRole();
    }

    private Role getDefaultRole() {
        return roleRepository.findByName(DEFAULT_ROLE)
                .orElseThrow(() -> new IllegalStateException("Default role is not configured"));
    }
}
