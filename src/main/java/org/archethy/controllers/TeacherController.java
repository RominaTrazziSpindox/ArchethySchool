package org.archethy.controllers;

import org.archethy.models.Teacher;
import org.archethy.services.TeacherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/teachers")
public class TeacherController {

    @Autowired
    private TeacherService service;


    @GetMapping("/all")
    public List<Teacher> getAll() {
        return service.getAllTeachers();
    }


    @GetMapping("/{id}")
    public Teacher getById(@PathVariable int id) {
        return service.getTeacherById(id);
    }
}
