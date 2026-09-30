package com.example.globit_java.controller;

import com.example.globit_java.dto.DepartmentDto;
import com.example.globit_java.service.DepartmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/departments")
public class DepartmentController {

    @Autowired
    private DepartmentService departmentService;

    // Bài 10 & 11: CRUD cho Department
    @PostMapping
    public ResponseEntity<DepartmentDto> create(@RequestBody DepartmentDto dto) {
        return new ResponseEntity<>(departmentService.create(dto), HttpStatus.CREATED);
    }

    @GetMapping
    public List<DepartmentDto> getAll() {
        return departmentService.getAll();
    }

    // Bài 11: viết api hiển thị thêm danh sách các Department thuộc Company đó
    @GetMapping("/by-company/{companyId}")
    public List<DepartmentDto> getByCompany(@PathVariable Long companyId) {
        return departmentService.getByCompany(companyId);
    }

    @GetMapping("/{id:[0-9]+}")
    public DepartmentDto getById(@PathVariable Long id) {
        return departmentService.getById(id);
    }

    @PutMapping("/{id:[0-9]+}")
    public DepartmentDto update(@PathVariable Long id, @RequestBody DepartmentDto dto) {
        return departmentService.update(id, dto);
    }

    @DeleteMapping("/{id:[0-9]+}")
    public String delete(@PathVariable Long id) {
        departmentService.delete(id);
        return "Deleted successfully";
    }
}
