package com.example.globit_java.controller;

import com.example.globit_java.dto.UserDto;
import com.example.globit_java.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserService userService;

    // Bài 5: CRUD cho User
    // Bài 9: Cho phép phân quyền cho User (Mỗi User được chọn nhiều Role)
    @PostMapping
    public ResponseEntity<UserDto> create(@RequestBody UserDto dto) {
        return new ResponseEntity<>(userService.create(dto), HttpStatus.CREATED);
    }

    @GetMapping
    public List<UserDto> getAll() {
        return userService.getAll();
    }

    @GetMapping("/page")
    public Page<UserDto> getAllPaged(Pageable pageable) {
        return userService.getAllPaged(pageable);
    }

    @GetMapping("/{id}")
    public UserDto getById(@PathVariable Long id) {
        return userService.getById(id);
    }

    @PutMapping("/{id}")
    public UserDto update(@PathVariable Long id, @RequestBody UserDto dto) {
        return userService.update(id, dto);
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable Long id) {
        userService.delete(id);
        return "Deleted successfully";
    }

    // Endpoint phân quyền
    @PutMapping("/{id}/roles")
    public UserDto assignRoles(@PathVariable Long id, @RequestBody List<Long> roleIds) {
        return userService.assignRoles(id, roleIds);
    }
}
