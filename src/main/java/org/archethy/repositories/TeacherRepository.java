package org.archethy.repositories;

import org.archethy.models.Teacher;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public class TeacherRepository implements IRepositoryRead<Teacher> {

    @Override
    public List<Teacher> getAll() {
        return List.of();
    }

    @Override
    public Teacher getById(int id) {
        return null;
    }
}


