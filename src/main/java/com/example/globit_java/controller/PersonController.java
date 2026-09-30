package com.example.globit_java.controller;

import com.example.globit_java.dto.PersonDto;
import com.example.globit_java.service.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/api/persons")
public class PersonController {

    @Autowired
    private PersonService personService;

    // Bài 5: CRUD cho Person
    // Bài 7: sửa lại CRUD của Person, cho phép chọn Company cho Person
    @PostMapping
    public ResponseEntity<PersonDto> create(@RequestBody PersonDto dto) {
        return new ResponseEntity<>(personService.create(dto), HttpStatus.CREATED);
    }

    @GetMapping
    public List<PersonDto> getAll() {
        return personService.getAll();
    }

    // Bài 12: Làm phân trang cho Person
    @GetMapping("/page")
    public Page<PersonDto> getAllPaged(Pageable pageable) {
        return personService.getAllPaged(pageable);
    }

    @GetMapping("/{id}")
    public PersonDto getById(@PathVariable Long id) {
        return personService.getById(id);
    }

    @PutMapping("/{id}")
    public PersonDto update(@PathVariable Long id, @RequestBody PersonDto dto) {
        return personService.update(id, dto);
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable Long id) {
        personService.delete(id);
        return "Deleted successfully";
    }

    // Bài 16: Cho phép upload ảnh person, lưu path ảnh avatar
    @PostMapping(value = "/{id}/avatar", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public PersonDto uploadAvatar(@PathVariable Long id, @RequestParam("file") MultipartFile file) {
        return personService.uploadAvatar(id, file);
    }
}
