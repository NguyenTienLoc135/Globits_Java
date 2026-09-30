package com.example.globit_java.controller;

import com.example.globit_java.dto.ProjectDto;
import com.example.globit_java.service.ProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/projects")
public class ProjectController {

    @Autowired
    private ProjectService projectService;

    // Bài 12: CRUD cho Project
    @PostMapping
    public ResponseEntity<ProjectDto> create(@RequestBody ProjectDto dto) {
        return new ResponseEntity<>(projectService.create(dto), HttpStatus.CREATED);
    }

    @GetMapping
    public List<ProjectDto> getAll() {
        return projectService.getAll();
    }

    // Bài 12: Làm phân trang cho Project
    @GetMapping("/page")
    public Page<ProjectDto> getAllPaged(Pageable pageable) {
        return projectService.getAllPaged(pageable);
    }

    @GetMapping("/{id}")
    public ProjectDto getById(@PathVariable Long id) {
        return projectService.getById(id);
    }

    @PutMapping("/{id}")
    public ProjectDto update(@PathVariable Long id, @RequestBody ProjectDto dto) {
        return projectService.update(id, dto);
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable Long id) {
        projectService.delete(id);
        return "Deleted successfully";
    }
}
