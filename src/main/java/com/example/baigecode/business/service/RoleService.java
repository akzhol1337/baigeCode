package com.example.baigecode.business.service;

import com.example.baigecode.business.entity.Role;
import com.example.baigecode.persistance.repository.RoleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RoleService {
    private final RoleRepository roleRepository;

    public Role saveRole(Role role) {
        return roleRepository.save(role);
    }
    public Role getRoleById(Long id) {
        return roleRepository.getById(id);
    }
}
