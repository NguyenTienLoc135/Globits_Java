package com.example.globit_java.controller;

import com.example.globit_java.dto.CountryDto;
import com.example.globit_java.service.CountryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/countries")
public class CountryController {

    @Autowired
    private CountryService countryService;

    // Bài 3: CRUD cho Country
    @PostMapping
    public ResponseEntity<CountryDto> create(@RequestBody CountryDto dto) {
        return new ResponseEntity<>(countryService.create(dto), HttpStatus.CREATED);
    }

    @GetMapping
    public List<CountryDto> getAll() {
        return countryService.getAll();
    }

    @GetMapping("/{id}")
    public CountryDto getById(@PathVariable Long id) {
        return countryService.getById(id);
    }

    @PutMapping("/{id}")
    public CountryDto update(@PathVariable Long id, @RequestBody CountryDto dto) {
        return countryService.update(id, dto);
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable Long id) {
        countryService.delete(id);
        return "Deleted successfully";
    }
}
