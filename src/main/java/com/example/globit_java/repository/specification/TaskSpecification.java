package com.example.globit_java.repository.specification;

import com.example.globit_java.dto.TaskFilterDto;
import com.example.globit_java.entity.Task;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;

public class TaskSpecification {

    public static Specification<Task> filterTasks(TaskFilterDto filter) {
        return (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();

            if (filter == null) {
                return cb.conjunction();
            }

            // Lọc theo Company (thông qua Project -> Company)
            if (filter.getCompanyId() != null) {
                predicates.add(cb.equal(root.get("project").get("company").get("id"), filter.getCompanyId()));
            }

            // Lọc theo Project
            if (filter.getProjectId() != null) {
                predicates.add(cb.equal(root.get("project").get("id"), filter.getProjectId()));
            }

            // Lọc theo Person
            if (filter.getPersonId() != null) {
                predicates.add(cb.equal(root.get("person").get("id"), filter.getPersonId()));
            }

            // Lọc theo Status (1: Mới tạo, 2: Đang làm, 3: Hoàn thành, 4: Tạm hoãn)
            if (filter.getStatus() != null) {
                predicates.add(cb.equal(root.get("status"), filter.getStatus()));
            }

            // Lọc theo Priority (1: Cao, 2: Trung bình, 3: Thấp)
            if (filter.getPriority() != null) {
                predicates.add(cb.equal(root.get("priority"), filter.getPriority()));
            }

            // Tìm kiếm theo Name (contains, ignore case)
            if (filter.getName() != null && !filter.getName().trim().isEmpty()) {
                predicates.add(cb.like(cb.lower(root.get("name")), "%" + filter.getName().trim().toLowerCase() + "%"));
            }

            return cb.and(predicates.toArray(new Predicate[0]));
        };
    }
}
