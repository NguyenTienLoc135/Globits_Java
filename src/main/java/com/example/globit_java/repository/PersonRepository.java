package com.example.globit_java.repository;

import com.example.globit_java.entity.Person;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PersonRepository extends JpaRepository<Person, Long> {
    List<Person> findByCompanyId(Long companyId);
    Page<Person> findByCompanyId(Long companyId, Pageable pageable);
}
