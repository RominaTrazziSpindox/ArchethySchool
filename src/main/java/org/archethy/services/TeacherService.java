package org.archethy.services;

import org.archethy.models.Teacher;
import org.archethy.repositories.TeacherRepository;

import java.util.List;

public class TeacherService {

    // Dependency Injection di Repository (= Autowired di StudentRepository)
    private final TeacherRepository repository;

    public TeacherService(TeacherRepository repository) {
        this.repository = repository;
    }
    // End

    // Metodi CRUD
    public List<Teacher> getAllTeachers() {
        return repository.getAll();
    }

    public Teacher getTeacherById(int id) {
        return repository.getById(id);
    }
}
