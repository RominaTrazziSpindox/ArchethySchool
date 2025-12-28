package org.archethy.repositories;

import org.archethy.models.Student;
import org.archethy.models.Teacher;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Repository
public class TeacherRepository implements IRepositoryRead<Teacher> {

    @Override
    public List<Teacher> getAll() {

        try (Connection conn = DBConnection.getConnection()) {

            List<Teacher> teacherList = new ArrayList<>();

            String sql = "SELECT teacher_id, first_name, last_name, teaching_subject  FROM teacher";

            PreparedStatement ps = conn.prepareStatement(sql);

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Teacher teacher = new Teacher();

                // Campi ereditati da Person
                teacher.setFirstname(rs.getString("first_name"));
                teacher.setLastname(rs.getString("last_name"));

                // Campi specifici di Teacher
                teacher.setTeacherId((rs.getInt("teacher_id")));
                teacher.setTeachingSubject((rs.getString("teaching_subject")));

                // Aggiungi alla lista da restituire
                teacherList.add(teacher);
            }

            // Restiuisci la lista di studenti letta dal db
            return teacherList;

        } catch (SQLException e) {
            System.err.println("Errore " + e.getErrorCode() + ": " + e.getMessage());
        }

        return null;
    }

    @Override
    public Teacher getById(int id) {

        try (Connection conn = DBConnection.getConnection()) {

            String sql = "SELECT teacher_id, first_name, last_name, teaching_subject  FROM teacher WHERE teacher_id = ?";

            PreparedStatement ps = conn.prepareStatement(sql);

            ps.setInt(1, id);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                Teacher teacher = new Teacher();
                teacher.setTeacherId((rs.getInt("teacher_id")));
                teacher.setFirstname(rs.getString("first_name"));
                teacher.setLastname(rs.getString("last_name"));
                teacher.setTeachingSubject(rs.getString("teaching_subject"));

                // Restituisci il singolo studente
                return teacher;
            }

        } catch (SQLException e) {
            System.err.println("Errore " + e.getErrorCode() + ": " + e.getMessage());
        }

        // Se la lettura non va a buon fine
        return null;
    }
}


