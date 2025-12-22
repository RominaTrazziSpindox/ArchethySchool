package org.archethy.repositories;

import org.archethy.models.Student;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class StudentRepository implements IRepositoryRead<Student>, IRepositoryWrite<Student> {

    private final List<Student> students = new ArrayList<>();

    @Override
    public List<Student> getAll() {
        return students;
    }


    @Override
    public Student getById(int id) {
        return students.stream()
                .filter(s -> s.getId() == id)
                .findFirst()
                .orElse(null);
    }

    public StudentRepository() {
        students.add(new Student("Alice", "Rossi", "STU001", "2001-05-21"));
        students.add(new Student("Marco", "Verdi", "STU002", "2000-12-10"));
    }


    @Override
    public boolean insert(Student obj) {
        return students.add(obj);
    }

    @Override
    public boolean update(Student obj) {
        for (int i = 0; i < students.size(); i++) {
            if (students.get(i).getId() == obj.getId()) {
                students.set(i, obj);
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean delete(int id) {
        return students.removeIf(s -> s.getId() == id);
    }
}


