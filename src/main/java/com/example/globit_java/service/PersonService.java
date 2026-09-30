package com.example.globit_java.service;

import com.example.globit_java.dto.PersonDto;
import com.example.globit_java.entity.Company;
import com.example.globit_java.entity.Person;
import com.example.globit_java.entity.User;
import com.example.globit_java.repository.CompanyRepository;
import com.example.globit_java.repository.PersonRepository;
import com.example.globit_java.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class PersonService {

    @Autowired
    private PersonRepository personRepository;

    @Autowired
    private CompanyRepository companyRepository;

    @Autowired
    private UserRepository userRepository;

    @Value("${file.upload-dir:./uploads}")
    private String uploadDir;

    private PersonDto toDto(Person p) {
        PersonDto dto = new PersonDto();
        dto.setId(p.getId());
        dto.setFullName(p.getFullName());
        dto.setGender(p.getGender());
        dto.setBirthdate(p.getBirthdate());
        dto.setPhoneNumber(p.getPhoneNumber());
        dto.setAddress(p.getAddress());
        dto.setAvatar(p.getAvatar());
        if (p.getCompany() != null) {
            dto.setCompanyId(p.getCompany().getId());
            dto.setCompanyName(p.getCompany().getName());
        }
        if (p.getUser() != null) {
            dto.setUserId(p.getUser().getId());
            dto.setUserEmail(p.getUser().getEmail());
        }
        return dto;
    }

    // Bài 5: CRUD cho Person
    // Bài 7: sửa lại CRUD của Person, cho phép chọn Company cho Person
    public PersonDto create(PersonDto dto) {
        Person person = new Person();
        person.setFullName(dto.getFullName());
        person.setGender(dto.getGender());
        person.setBirthdate(dto.getBirthdate());
        person.setPhoneNumber(dto.getPhoneNumber());
        person.setAddress(dto.getAddress());

        if (dto.getCompanyId() != null) {
            Company company = companyRepository.findById(dto.getCompanyId()).orElse(null);
            person.setCompany(company);
        }

        if (dto.getUserId() != null) {
            User user = userRepository.findById(dto.getUserId()).orElse(null);
            person.setUser(user);
        }

        return toDto(personRepository.save(person));
    }

    public List<PersonDto> getAll() {
        return personRepository.findAll().stream().map(this::toDto).collect(Collectors.toList());
    }

    // Bài 12: Làm phân trang cho Person
    public Page<PersonDto> getAllPaged(Pageable pageable) {
        return personRepository.findAll(pageable).map(this::toDto);
    }

    public PersonDto getById(Long id) {
        Person person = personRepository.findById(id).orElse(null);
        return person != null ? toDto(person) : null;
    }

    public PersonDto update(Long id, PersonDto dto) {
        Person person = personRepository.findById(id).orElse(null);
        if (person == null) return null;

        person.setFullName(dto.getFullName());
        person.setGender(dto.getGender());
        person.setBirthdate(dto.getBirthdate());
        person.setPhoneNumber(dto.getPhoneNumber());
        person.setAddress(dto.getAddress());

        if (dto.getCompanyId() != null) {
            Company company = companyRepository.findById(dto.getCompanyId()).orElse(null);
            person.setCompany(company);
        } else {
            person.setCompany(null);
        }

        return toDto(personRepository.save(person));
    }

    public void delete(Long id) {
        personRepository.deleteById(id);
    }

    // Bài 16: Bảng person thêm một trường avatar(string) lưu path ảnh avatar, làm tính năng cho phép upload ảnh person
    public PersonDto uploadAvatar(Long id, MultipartFile file) {
        Person person = personRepository.findById(id).orElse(null);
        if (person == null) return null;

        try {
            Path folder = Paths.get(uploadDir);
            if (!Files.exists(folder)) {
                Files.createDirectories(folder);
            }
            String filename = UUID.randomUUID() + "_" + file.getOriginalFilename();
            Path target = folder.resolve(filename);
            Files.copy(file.getInputStream(), target, StandardCopyOption.REPLACE_EXISTING);

            person.setAvatar("/uploads/" + filename);
            return toDto(personRepository.save(person));
        } catch (IOException e) {
            throw new RuntimeException("Upload failed: " + e.getMessage());
        }
    }
}
