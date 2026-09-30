package com.example.globit_java.controller;

import com.example.globit_java.dto.RoleDto;
import com.example.globit_java.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/roles")
public class RoleController {

    @Autowired
    private RoleService roleService;

    // Bài 9: CRUD cho Role
    @PostMapping
    public ResponseEntity<RoleDto> create(@RequestBody RoleDto dto) {
        return new ResponseEntity<>(roleService.create(dto), HttpStatus.CREATED);
    }

    @GetMapping
    public List<RoleDto> getAll() {
        return roleService.getAll();
    }

    @GetMapping("/{id}")
    public RoleDto getById(@PathVariable Long id) {
        return roleService.getById(id);
    }

    @PutMapping("/{id}")
    public RoleDto update(@PathVariable Long id, @RequestBody RoleDto dto) {
        return roleService.update(id, dto);
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable Long id) {
        roleService.delete(id);
        return "Deleted successfully";
    }
}
