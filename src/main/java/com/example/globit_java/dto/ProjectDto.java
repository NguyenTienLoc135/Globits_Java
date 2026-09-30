package com.example.globit_java.dto;

import java.util.HashSet;
import java.util.Set;

public class ProjectDto {
    private Long id;
    private String code;
    private String name;
    private String description;
    private Long companyId;
    private String companyName;
    private Set<Long> personIds = new HashSet<>();

    public ProjectDto() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Long getCompanyId() {
        return companyId;
    }

    public void setCompanyId(Long companyId) {
        this.companyId = companyId;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public Set<Long> getPersonIds() {
        return personIds;
    }

    public void setPersonIds(Set<Long> personIds) {
        this.personIds = personIds;
    }
}
