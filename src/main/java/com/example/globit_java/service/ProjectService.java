package com.example.globit_java.service;

import com.example.globit_java.dto.ProjectDto;
import com.example.globit_java.entity.Company;
import com.example.globit_java.entity.Person;
import com.example.globit_java.entity.Project;
import com.example.globit_java.exception.BadRequestException;
import com.example.globit_java.repository.CompanyRepository;
import com.example.globit_java.repository.PersonRepository;
import com.example.globit_java.repository.ProjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProjectService {

    @Autowired
    private ProjectRepository projectRepository;

    @Autowired
    private CompanyRepository companyRepository;

    @Autowired
    private PersonRepository personRepository;

    private ProjectDto toDto(Project p) {
        ProjectDto dto = new ProjectDto();
        dto.setId(p.getId());
        dto.setCode(p.getCode());
        dto.setName(p.getName());
        dto.setDescription(p.getDescription());
        if (p.getCompany() != null) {
            dto.setCompanyId(p.getCompany().getId());
            dto.setCompanyName(p.getCompany().getName());
        }
        if (p.getPersons() != null) {
            dto.setPersonIds(p.getPersons().stream().map(Person::getId).collect(Collectors.toSet()));
        }
        return dto;
    }

    // Bài 12: Khi create/edit project, có thể chọn Company, sau khi chọn company, có thể chọn nhiều Person tham gia vào dự án từ danh sách Person thuộc Company vừa chọn
    public ProjectDto create(ProjectDto dto) {
        Company company = companyRepository.findById(dto.getCompanyId()).orElse(null);

        Project project = new Project(dto.getCode(), dto.getName(), dto.getDescription());
        project.setCompany(company);

        if (dto.getPersonIds() != null && !dto.getPersonIds().isEmpty()) {
            List<Person> persons = personRepository.findAllById(dto.getPersonIds());
            for (Person p : persons) {
                if (p.getCompany() == null || !p.getCompany().getId().equals(company.getId())) {
                    throw new BadRequestException("Person " + p.getFullName() + " does not belong to Company");
                }
            }
            project.setPersons(new HashSet<>(persons));
        }

        return toDto(projectRepository.save(project));
    }

    public List<ProjectDto> getAll() {
        return projectRepository.findAll().stream().map(this::toDto).collect(Collectors.toList());
    }

    // Bài 12: Làm phân trang cho Project
    public Page<ProjectDto> getAllPaged(Pageable pageable) {
        return projectRepository.findAll(pageable).map(this::toDto);
    }

    public ProjectDto getById(Long id) {
        Project p = projectRepository.findById(id).orElse(null);
        return p != null ? toDto(p) : null;
    }

    public ProjectDto update(Long id, ProjectDto dto) {
        Project project = projectRepository.findById(id).orElse(null);
        if (project == null) return null;

        project.setCode(dto.getCode());
        project.setName(dto.getName());
        project.setDescription(dto.getDescription());

        if (dto.getPersonIds() != null) {
            List<Person> persons = personRepository.findAllById(dto.getPersonIds());
            for (Person p : persons) {
                if (p.getCompany() == null || !p.getCompany().getId().equals(project.getCompany().getId())) {
                    throw new BadRequestException("Person " + p.getFullName() + " does not belong to Company");
                }
            }
            project.setPersons(new HashSet<>(persons));
        }

        return toDto(projectRepository.save(project));
    }

    public void delete(Long id) {
        projectRepository.deleteById(id);
    }
}
