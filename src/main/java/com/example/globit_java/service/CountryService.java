package com.example.globit_java.service;

import com.example.globit_java.dto.CountryDto;
import com.example.globit_java.entity.Country;
import com.example.globit_java.repository.CountryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CountryService {

    @Autowired
    private CountryRepository countryRepository;

    private CountryDto toDto(Country c) {
        return new CountryDto(c.getId(), c.getName(), c.getCode(), c.getDescription());
    }

    public CountryDto create(CountryDto dto) {
        Country country = new Country(dto.getName(), dto.getCode(), dto.getDescription());
        return toDto(countryRepository.save(country));
    }

    public List<CountryDto> getAll() {
        return countryRepository.findAll().stream().map(this::toDto).collect(Collectors.toList());
    }

    public CountryDto getById(Long id) {
        Country country = countryRepository.findById(id).orElse(null);
        return country != null ? toDto(country) : null;
    }

    public CountryDto update(Long id, CountryDto dto) {
        Country country = countryRepository.findById(id).orElse(null);
        if (country == null) return null;
        country.setName(dto.getName());
        country.setCode(dto.getCode());
        country.setDescription(dto.getDescription());
        return toDto(countryRepository.save(country));
    }

    public void delete(Long id) {
        countryRepository.deleteById(id);
    }
}
