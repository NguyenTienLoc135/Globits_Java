package com.example.globit_java.repository;

import com.example.globit_java.entity.Project;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProjectRepository extends JpaRepository<Project, Long> {
    Optional<Project> findByCode(String code);
    boolean existsByCode(String code);
    List<Project> findByCompanyId(Long companyId);
    Page<Project> findByCompanyId(Long companyId, Pageable pageable);
}
