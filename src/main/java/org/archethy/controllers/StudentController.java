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

       Student s = service.getStudentById(id);
       System.out.println("studentId = " + s.getStudentId());
       System.out.println("class = " + s.getClass());

        return service.getStudentById(id);
    }


    /*
    @PostMapping("/insert")
    public boolean insert(@RequestBody Student s) {
        return service.addStudent(s);
    }

    @PutMapping("/update")
    public boolean update(@RequestBody Student s) {
        return service.updateStudent(s);
    }

    @DeleteMapping("/delete/{id}")
    public boolean delete(@PathVariable int id) {
        return service.deleteStudent(id);
    } */
}