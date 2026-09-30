package com.example.globit_java.dto;

public class TaskFilterDto {
    private Long companyId;
    private Long projectId;
    private Long personId;
    private Integer status;
    private Integer priority;
    private String name;

    public TaskFilterDto() {
    }

    public TaskFilterDto(Long companyId, Long projectId, Long personId, Integer status, Integer priority, String name) {
        this.companyId = companyId;
        this.projectId = projectId;
        this.personId = personId;
        this.status = status;
        this.priority = priority;
        this.name = name;
    }

    public Long getCompanyId() {
        return companyId;
    }

    public void setCompanyId(Long companyId) {
        this.companyId = companyId;
    }

    public Long getProjectId() {
        return projectId;
    }

    public void setProjectId(Long projectId) {
        this.projectId = projectId;
    }

    public Long getPersonId() {
        return personId;
    }

    public void setPersonId(Long personId) {
        this.personId = personId;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getPriority() {
        return priority;
    }

    public void setPriority(Integer priority) {
        this.priority = priority;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
