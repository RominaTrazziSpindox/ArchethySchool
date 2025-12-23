package org.archethy.services;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.archethy.repositories.StudentRepository;
import org.archethy.models.Student;
import java.util.List;

@Service
public class StudentService {


    // Dependency Injection di Repository (= Autowired di StudentRepository)
    private final StudentRepository repository;

    public StudentService(StudentRepository repository) {
        this.repository = repository;
    }
    // End


    // Metodi CRUD
    public List<Student> getAllStudents() {
        return repository.getAll();
    }

    public Student getStudentById(int id) {
        return repository.getById(id);
    }

    public boolean addStudent(Student student) {
        return repository.insert(student);
    }



 /*
    public boolean updateStudent(Student s) {
        return repository.update(s);
    }

    public boolean deleteStudent(int id) {
        return repository.delete(id);
    } */
}
