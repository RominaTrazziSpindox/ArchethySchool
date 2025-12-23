package org.archethy.repositories;

import org.archethy.models.Student;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Repository
public class StudentRepository implements IRepositoryRead<Student> {


    // Metodo per ottenere la Lista completa degli studenti
    @Override
    public List<Student> getAll() {

        // Crea lista vuota
        List<Student> studentList = new ArrayList<>();

        // Prova di connessione al database
        try (Connection conn = DBConnection.getConnection()) {

            // Query da eseguire
            String sql = "SELECT student_id, first_name, last_name, student_number, date_of_birth FROM student";

            // Metodo anti SQL Injection
            PreparedStatement ps = conn.prepareStatement(sql);

            // Esegui la query
            ResultSet rs = ps.executeQuery();

            // Continua a leggere finché esiste un record successivo all'interno del database
            while (rs.next()) {
                Student student = new Student();

                // Campi ereditati da Person
                student.setFirstname(rs.getString("first_name"));
                student.setLastname(rs.getString("last_name"));

                // Campi specifici di Student
                student.setStudentId((rs.getInt("student_id")));
                student.setDateOfBirth(rs.getDate("date_of_birth").toLocalDate());
                student.setStudentNumber(rs.getString("student_number"));

                // Aggiungi alla lista da restituire
                studentList.add(student);
            }
        } catch (SQLException e) {
            System.err.println("Errore nella query: " + e.getErrorCode() + " " + e.getMessage());
        }

        // Restiuisci la lista di studenti letta dal db
        return studentList;
    }


    // Metodo per ottenere i dati di un solo studente tramite l'id
    @Override
    public Student getById(int id) {

        // Crea lista vuota
        List<Student> studentList = new ArrayList<>();

        // Prova di connessione al database
        try (Connection conn = DBConnection.getConnection()) {

            // Query SQL parametrizzata da eseguire
            // - seleziona i campi necessari dalla tabella Students
            // - usa il placeholder '?' per evitare SQL Injection
            String sql = "SELECT student_id, first_name, last_name, student_number, date_of_birth FROM student WHERE student_id = ?";

            // Metodo anti SQL Injection
            PreparedStatement ps = conn.prepareStatement(sql);

            // Sostituisce il placeholder '?' con il valore dell'id passato al metodo. 1 è il numero della colonna sul db.
            ps.setInt(1, id);

            // Prova a eseguire la query e ottenere l'oggetto ResultSet
            ResultSet rs = ps.executeQuery();

            // Continua a leggere finché esiste un record successivo all'interno del database
            if (rs.next()) {
                Student student = new Student();
                student.setStudentId((rs.getInt("student_id")));
                student.setFirstname(rs.getString("first_name"));
                student.setLastname(rs.getString("last_name"));
                student.setStudentNumber(rs.getString("student_number"));
                student.setDateOfBirth(rs.getDate("date_of_birth").toLocalDate());
                return student;
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return null;

    }
}


/*
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
}*/


