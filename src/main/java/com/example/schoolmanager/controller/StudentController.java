package com.example.schoolmanager.controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*; // Import tất cả annotation để tránh thiếu sót

import com.example.schoolmanager.service.StudentService;
import com.example.schoolmanager.model.Student;

@RestController
@RequestMapping("/api/students")
@CrossOrigin // Cho phép frontend gọi API từ cổng khác
public class StudentController {

    @Autowired
    private StudentService service;

    // 1. API thêm sinh viên: Đã thêm @RequestBody để tránh bị null
    @PostMapping
    public Student addStudent(@RequestBody Student student) {
        return service.addStudent(student);
    }

    // 2. API xóa sinh viên: Sử dụng PathVariable để lấy ID từ URL
    @DeleteMapping("/{id}")
    public String deleteStudent(@PathVariable int id) {
        service.deleteStudent(id);
        return "Student with ID " + id + " has been deleted.";
    }

    // 3. Tìm kiếm sinh viên theo tên: Sử dụng RequestParam (?name=...)
    @GetMapping("/search")
    public List<Student> searchByName(@RequestParam String name) {
        return service.findByName(name);
    }

    // 4. API lấy sinh viên theo ID
    @GetMapping("/{id}")
    public Student getStudentById(@PathVariable int id) {
        return service.getStudentById(id);
    }

    // 5. API lấy danh sách sinh viên
    @GetMapping
    public List<Student> getAllStudents() {
        return service.getAll();
    }

    // 6. API cập nhật sinh viên
    @PutMapping("/{id}") // Thay đổi từ @PostMapping("/update/{id}")
    public Student updateStudent(@PathVariable int id, @RequestBody Student studentDetails) {
        Student existingStudent = service.getStudentById(id);
        if (existingStudent != null) {
            existingStudent.setName(studentDetails.getName());
            existingStudent.setEmail(studentDetails.getEmail());
            return service.addStudent(existingStudent);
        }
        return null;
    }
}