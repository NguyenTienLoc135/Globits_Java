package com.example.globit_java.service;

import com.example.globit_java.dto.DepartmentDto;
import com.example.globit_java.entity.Company;
import com.example.globit_java.entity.Department;
import com.example.globit_java.repository.CompanyRepository;
import com.example.globit_java.repository.DepartmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class DepartmentService {

    @Autowired
    private DepartmentRepository departmentRepository;

    @Autowired
    private CompanyRepository companyRepository;

    private DepartmentDto toDto(Department d) {
        DepartmentDto dto = new DepartmentDto();
        dto.setId(d.getId());
        dto.setCode(d.getCode());
        dto.setName(d.getName());
        if (d.getCompany() != null) {
            dto.setCompanyId(d.getCompany().getId());
            dto.setCompanyName(d.getCompany().getName());
        }
        if (d.getParent() != null) {
            dto.setParentId(d.getParent().getId());
            dto.setParentName(d.getParent().getName());
        }
        return dto;
    }

    // Bài 10: Tạo model Department(Phòng ban) gồm các trường: code, name, parent_id, company_id
    // Mỗi phòng ban thuộc 1 Company, có thể chọn Phòng ban cha(parent_id)
    public DepartmentDto create(DepartmentDto dto) {
        Company company = companyRepository.findById(dto.getCompanyId()).orElse(null);

        Department dept = new Department(dto.getCode(), dto.getName());
        dept.setCompany(company);

        if (dto.getParentId() != null) {
            Department parent = departmentRepository.findById(dto.getParentId()).orElse(null);
            dept.setParent(parent);
        }

        return toDto(departmentRepository.save(dept));
    }

    public List<DepartmentDto> getAll() {
        return departmentRepository.findAll().stream().map(this::toDto).collect(Collectors.toList());
    }

    public DepartmentDto getById(Long id) {
        Department d = departmentRepository.findById(id).orElse(null);
        return d != null ? toDto(d) : null;
    }

    public DepartmentDto update(Long id, DepartmentDto dto) {
        Department dept = departmentRepository.findById(id).orElse(null);
        if (dept == null) return null;

        dept.setCode(dto.getCode());
        dept.setName(dto.getName());

        if (dto.getParentId() != null) {
            Department parent = departmentRepository.findById(dto.getParentId()).orElse(null);
            dept.setParent(parent);
        } else {
            dept.setParent(null);
        }

        return toDto(departmentRepository.save(dept));
    }

    public void delete(Long id) {
        departmentRepository.deleteById(id);
    }

    // Bài 11: viết api hiển thị thêm danh sách các Department thuộc Company đó
    public List<DepartmentDto> getByCompany(Long companyId) {
        return departmentRepository.findByCompanyId(companyId).stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }
}
