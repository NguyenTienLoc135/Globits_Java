package com.example.globit_java.service;

import com.example.globit_java.dto.TaskDto;
import com.example.globit_java.dto.TaskFilterDto;
import com.example.globit_java.entity.Person;
import com.example.globit_java.entity.Project;
import com.example.globit_java.entity.Task;
import com.example.globit_java.repository.PersonRepository;
import com.example.globit_java.repository.ProjectRepository;
import com.example.globit_java.repository.TaskRepository;
import com.example.globit_java.repository.specification.TaskSpecification;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class TaskService {

    @Autowired
    private TaskRepository taskRepository;

    @Autowired
    private ProjectRepository projectRepository;

    @Autowired
    private PersonRepository personRepository;

    private TaskDto toDto(Task t) {
        TaskDto dto = new TaskDto();
        dto.setId(t.getId());
        dto.setName(t.getName());
        dto.setDescription(t.getDescription());
        dto.setStartTime(t.getStartTime());
        dto.setEndTime(t.getEndTime());
        dto.setPriority(t.getPriority());
        dto.setStatus(t.getStatus());

        if (t.getProject() != null) {
            dto.setProjectId(t.getProject().getId());
            dto.setProjectName(t.getProject().getName());
        }
        if (t.getPerson() != null) {
            dto.setPersonId(t.getPerson().getId());
            dto.setPersonName(t.getPerson().getFullName());
        }
        return dto;
    }

    // Bài 13: CRUD cho Task
    public TaskDto create(TaskDto dto) {
        Project project = projectRepository.findById(dto.getProjectId()).orElse(null);

        Task task = new Task();
        task.setName(dto.getName());
        task.setDescription(dto.getDescription());
        task.setStartTime(dto.getStartTime());
        task.setEndTime(dto.getEndTime());
        task.setPriority(dto.getPriority() != null ? dto.getPriority() : 2); // 1: Cao, 2: Trung bình, 3: Thấp
        task.setStatus(dto.getStatus() != null ? dto.getStatus() : 1); // 1: Mới tạo, 2: Đang làm, 3: Hoàn thành, 4: Tạm hoãn
        task.setProject(project);

        if (dto.getPersonId() != null) {
            Person person = personRepository.findById(dto.getPersonId()).orElse(null);
            task.setPerson(person);
        }

        return toDto(taskRepository.save(task));
    }

    public List<TaskDto> getAll() {
        return taskRepository.findAll().stream().map(this::toDto).collect(Collectors.toList());
    }

    public TaskDto getById(Long id) {
        Task t = taskRepository.findById(id).orElse(null);
        return t != null ? toDto(t) : null;
    }

    public TaskDto update(Long id, TaskDto dto) {
        Task task = taskRepository.findById(id).orElse(null);
        if (task == null) return null;

        task.setName(dto.getName());
        task.setDescription(dto.getDescription());
        task.setStartTime(dto.getStartTime());
        task.setEndTime(dto.getEndTime());
        if (dto.getPriority() != null) task.setPriority(dto.getPriority());
        if (dto.getStatus() != null) task.setStatus(dto.getStatus());

        if (dto.getProjectId() != null) {
            Project project = projectRepository.findById(dto.getProjectId()).orElse(null);
            task.setProject(project);
        }

        if (dto.getPersonId() != null) {
            Person person = personRepository.findById(dto.getPersonId()).orElse(null);
            task.setPerson(person);
        } else {
            task.setPerson(null);
        }

        return toDto(taskRepository.save(task));
    }

    public void delete(Long id) {
        taskRepository.deleteById(id);
    }

    // Bài 14: api danh sách Task cần lọc theo Company, Project, Person, Status, priority, tìm kiếm theo name
    public Page<TaskDto> filterTasksPaged(TaskFilterDto filter, Pageable pageable) {
        Specification<Task> spec = TaskSpecification.filterTasks(filter);
        return taskRepository.findAll(spec, pageable).map(this::toDto);
    }

    // Bài 15: Viết API xuất excel cho Task gồm các cột: Project, Description, Start Time, End Time, Priority, Status, Person
    public ByteArrayInputStream exportExcel(TaskFilterDto filter) {
        Specification<Task> spec = TaskSpecification.filterTasks(filter);
        List<Task> tasks = taskRepository.findAll(spec);

        try (Workbook workbook = new XSSFWorkbook(); ByteArrayOutputStream out = new ByteArrayOutputStream()) {
            Sheet sheet = workbook.createSheet("Tasks");
            String[] headers = {"Project", "Description", "Start Time", "End Time", "Priority", "Status", "Person"};

            Row headerRow = sheet.createRow(0);
            for (int i = 0; i < headers.length; i++) {
                Cell cell = headerRow.createCell(i);
                cell.setCellValue(headers[i]);
            }

            int rowIdx = 1;
            for (Task t : tasks) {
                Row row = sheet.createRow(rowIdx++);
                row.createCell(0).setCellValue(t.getProject() != null ? t.getProject().getName() : "");
                row.createCell(1).setCellValue(t.getDescription() != null ? t.getDescription() : "");
                row.createCell(2).setCellValue(t.getStartTime() != null ? t.getStartTime().toString() : "");
                row.createCell(3).setCellValue(t.getEndTime() != null ? t.getEndTime().toString() : "");
                row.createCell(4).setCellValue(t.getPriority() != null ? String.valueOf(t.getPriority()) : "");
                row.createCell(5).setCellValue(t.getStatus() != null ? String.valueOf(t.getStatus()) : "");
                row.createCell(6).setCellValue(t.getPerson() != null ? t.getPerson().getFullName() : "");
            }

            workbook.write(out);
            return new ByteArrayInputStream(out.toByteArray());
        } catch (IOException e) {
            throw new RuntimeException("Export excel error: " + e.getMessage());
        }
    }
}
