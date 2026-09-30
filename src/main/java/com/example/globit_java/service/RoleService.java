package com.example.globit_java.service;

import com.example.globit_java.dto.RoleDto;
import com.example.globit_java.entity.Role;
import com.example.globit_java.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class RoleService {

    @Autowired
    private RoleRepository roleRepository;

    private RoleDto toDto(Role r) {
        return new RoleDto(r.getId(), r.getRole(), r.getDescription());
    }

    // Bài 9: CRUD cho Role
    public RoleDto create(RoleDto dto) {
        Role role = new Role(dto.getRole(), dto.getDescription());
        return toDto(roleRepository.save(role));
    }

    public List<RoleDto> getAll() {
        return roleRepository.findAll().stream().map(this::toDto).collect(Collectors.toList());
    }

    public RoleDto getById(Long id) {
        Role role = roleRepository.findById(id).orElse(null);
        return role != null ? toDto(role) : null;
    }

    public RoleDto update(Long id, RoleDto dto) {
        Role role = roleRepository.findById(id).orElse(null);
        if (role == null) return null;
        role.setRole(dto.getRole());
        role.setDescription(dto.getDescription());
        return toDto(roleRepository.save(role));
    }

    public void delete(Long id) {
        roleRepository.deleteById(id);
    }
}
