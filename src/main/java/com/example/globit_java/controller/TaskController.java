package com.example.globit_java.controller;

import com.example.globit_java.dto.TaskDto;
import com.example.globit_java.dto.TaskFilterDto;
import com.example.globit_java.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.ByteArrayInputStream;
import java.util.List;

@RestController
@RequestMapping("/api/tasks")
public class TaskController {

    @Autowired
    private TaskService taskService;

    // Bài 13: CRUD cho bảng Task
    @PostMapping
    public ResponseEntity<TaskDto> create(@RequestBody TaskDto dto) {
        return new ResponseEntity<>(taskService.create(dto), HttpStatus.CREATED);
    }

    @GetMapping
    public List<TaskDto> getAll() {
        return taskService.getAll();
    }

    // Bài 14: api danh sách Task cần lọc theo Company, Project, Person, Status, priority, tìm kiếm theo name (có phân trang)
    @GetMapping("/filter")
    public Page<TaskDto> filterTasks(TaskFilterDto filter, Pageable pageable) {
        return taskService.filterTasksPaged(filter, pageable);
    }

    // Bài 15: Viết API xuất excel cho Task
    @GetMapping("/export-excel")
    public ResponseEntity<InputStreamResource> exportExcel(TaskFilterDto filter) {
        ByteArrayInputStream in = taskService.exportExcel(filter);
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Disposition", "attachment; filename=tasks.xlsx");
        return ResponseEntity.ok()
                .headers(headers)
                .contentType(MediaType.parseMediaType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"))
                .body(new InputStreamResource(in));
    }

    @GetMapping("/{id:[0-9]+}")
    public TaskDto getById(@PathVariable Long id) {
        return taskService.getById(id);
    }

    @PutMapping("/{id:[0-9]+}")
    public TaskDto update(@PathVariable Long id, @RequestBody TaskDto dto) {
        return taskService.update(id, dto);
    }

    @DeleteMapping("/{id:[0-9]+}")
    public String delete(@PathVariable Long id) {
        taskService.delete(id);
        return "Deleted successfully";
    }
}
