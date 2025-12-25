package org.archethy.controllers;

import org.springframework.web.bind.annotation.*;
import org.archethy.services.StudentService;
import org.archethy.models.Student;
import java.util.List;

@RestController
@RequestMapping("/api/students")
public class StudentController {

    // Dependency Injection di Repository (= Autowired di StudentService)
    private final StudentService service;

    public StudentController(StudentService service) {
        this.service = service;
    }
    // End

    @GetMapping("/all")
    public List<Student> getAll() {
        return service.getAllStudents();
    }

   @GetMapping("/{id}")
    public Student getById(@PathVariable int id) {
        return service.getStudentById(id);
    }

    @PostMapping("/insert")
    public boolean insert(@RequestBody Student student) {
        return service.addStudent(student);
    }
/*
    @PutMapping("/update")
    public boolean update(@RequestBody Student student) {
        return service.updateStudent(s);
    } */

    @DeleteMapping("/{id}")
    public boolean delete(@PathVariable int id) {
        return service.deleteStudent(id);
    }
}