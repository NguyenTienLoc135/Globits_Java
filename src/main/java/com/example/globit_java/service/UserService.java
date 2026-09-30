package com.example.globit_java.service;

import com.example.globit_java.dto.RoleDto;
import com.example.globit_java.dto.UserDto;
import com.example.globit_java.entity.Person;
import com.example.globit_java.entity.User;
import com.example.globit_java.repository.PersonRepository;
import com.example.globit_java.repository.RoleRepository;
import com.example.globit_java.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private PersonRepository personRepository;

    private UserDto toDto(User u) {
        UserDto dto = new UserDto();
        dto.setId(u.getId());
        dto.setEmail(u.getEmail());
        dto.setIsActive(u.getIsActive());
        if (u.getPerson() != null) {
            dto.setPersonId(u.getPerson().getId());
            dto.setPersonFullName(u.getPerson().getFullName());
        }
        if (u.getRoles() != null) {
            dto.setRoles(u.getRoles().stream()
                    .map(r -> new RoleDto(r.getId(), r.getRole(), r.getDescription()))
                    .collect(Collectors.toSet()));
        }
        return dto;
    }

    // Bài 5: CRUD cho User
    // Bài 9: Cho phép phân quyền cho User (Mỗi User được chọn nhiều Role)
    public UserDto create(UserDto dto) {
        User user = new User(dto.getEmail(), dto.getPassword(), dto.getIsActive() != null ? dto.getIsActive() : true);
        if (dto.getRoleIds() != null && !dto.getRoleIds().isEmpty()) {
            user.setRoles(new HashSet<>(roleRepository.findAllById(dto.getRoleIds())));
        }
        if (dto.getPersonId() != null) {
            Person person = personRepository.findById(dto.getPersonId()).orElse(null);
            if (person != null) {
                user = userRepository.save(user);
                person.setUser(user);
                personRepository.save(person);
                user.setPerson(person);
                return toDto(user);
            }
        }
        return toDto(userRepository.save(user));
    }

    public List<UserDto> getAll() {
        return userRepository.findAll().stream().map(this::toDto).collect(Collectors.toList());
    }

    public Page<UserDto> getAllPaged(Pageable pageable) {
        return userRepository.findAll(pageable).map(this::toDto);
    }

    public UserDto getById(Long id) {
        User user = userRepository.findById(id).orElse(null);
        return user != null ? toDto(user) : null;
    }

    public UserDto update(Long id, UserDto dto) {
        User user = userRepository.findById(id).orElse(null);
        if (user == null) return null;
        user.setEmail(dto.getEmail());
        if (dto.getPassword() != null && !dto.getPassword().trim().isEmpty()) {
            user.setPassword(dto.getPassword());
        }
        if (dto.getIsActive() != null) {
            user.setIsActive(dto.getIsActive());
        }
        if (dto.getRoleIds() != null) {
            user.setRoles(new HashSet<>(roleRepository.findAllById(dto.getRoleIds())));
        }
        return toDto(userRepository.save(user));
    }

    public void delete(Long id) {
        userRepository.deleteById(id);
    }

    // Bài 9: API phân quyền cho User
    public UserDto assignRoles(Long id, List<Long> roleIds) {
        User user = userRepository.findById(id).orElse(null);
        if (user == null) return null;
        user.setRoles(new HashSet<>(roleRepository.findAllById(roleIds)));
        return toDto(userRepository.save(user));
    }
}
