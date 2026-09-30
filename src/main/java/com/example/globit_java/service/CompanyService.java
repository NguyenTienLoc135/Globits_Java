package com.example.globit_java.service;

import com.example.globit_java.dto.CompanyDto;
import com.example.globit_java.dto.DepartmentDto;
import com.example.globit_java.entity.Company;
import com.example.globit_java.repository.CompanyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CompanyService {

    @Autowired
    private CompanyRepository companyRepository;

    private CompanyDto toDto(Company c) {
        return new CompanyDto(c.getId(), c.getName(), c.getCode(), c.getAddress());
    }

    public CompanyDto create(CompanyDto dto) {
        Company company = new Company(dto.getName(), dto.getCode(), dto.getAddress());
        return toDto(companyRepository.save(company));
    }

    public List<CompanyDto> getAll() {
        return companyRepository.findAll().stream().map(this::toDto).collect(Collectors.toList());
    }

    // Bài 12: Làm phân trang cho Company
    public Page<CompanyDto> getAllPaged(Pageable pageable) {
        return companyRepository.findAll(pageable).map(this::toDto);
    }

    public CompanyDto getById(Long id) {
        Company company = companyRepository.findById(id).orElse(null);
        return company != null ? toDto(company) : null;
    }

    public CompanyDto update(Long id, CompanyDto dto) {
        Company company = companyRepository.findById(id).orElse(null);
        if (company == null) return null;
        company.setName(dto.getName());
        company.setCode(dto.getCode());
        company.setAddress(dto.getAddress());
        return toDto(companyRepository.save(company));
    }

    public void delete(Long id) {
        companyRepository.deleteById(id);
    }

    // Bài 11: viết api hiển thị thêm danh sách các Department thuộc Company đó
    public List<DepartmentDto> getDepartments(Long companyId) {
        Company company = companyRepository.findById(companyId).orElse(null);
        if (company == null || company.getDepartments() == null) return new ArrayList<>();

        return company.getDepartments().stream().map(d -> {
            DepartmentDto dto = new DepartmentDto();
            dto.setId(d.getId());
            dto.setCode(d.getCode());
            dto.setName(d.getName());
            dto.setCompanyId(companyId);
            dto.setCompanyName(company.getName());
            if (d.getParent() != null) {
                dto.setParentId(d.getParent().getId());
                dto.setParentName(d.getParent().getName());
            }
            return dto;
        }).collect(Collectors.toList());
    }
}
