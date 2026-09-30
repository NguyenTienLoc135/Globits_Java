package com.example.globit_java.controller;

import com.example.globit_java.dto.CompanyDto;
import com.example.globit_java.dto.DepartmentDto;
import com.example.globit_java.service.CompanyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/companies")
public class CompanyController {

    @Autowired
    private CompanyService companyService;

    // Bài 7: CRUD cho Company
    @PostMapping
    public ResponseEntity<CompanyDto> create(@RequestBody CompanyDto dto) {
        return new ResponseEntity<>(companyService.create(dto), HttpStatus.CREATED);
    }

    @GetMapping
    public List<CompanyDto> getAll() {
        return companyService.getAll();
    }

    // Bài 12: Làm phân trang cho Company
    @GetMapping("/page")
    public Page<CompanyDto> getAllPaged(Pageable pageable) {
        return companyService.getAllPaged(pageable);
    }

    @GetMapping("/{id}")
    public CompanyDto getById(@PathVariable Long id) {
        return companyService.getById(id);
    }

    @PutMapping("/{id}")
    public CompanyDto update(@PathVariable Long id, @RequestBody CompanyDto dto) {
        return companyService.update(id, dto);
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable Long id) {
        companyService.delete(id);
        return "Deleted successfully";
    }

    // Bài 11: viết api hiển thị thêm danh sách các Department thuộc Company đó
    @GetMapping("/{id}/departments")
    public List<DepartmentDto> getDepartments(@PathVariable Long id) {
        return companyService.getDepartments(id);
    }
}
