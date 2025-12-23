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
public class StudentRepository {

    // Metodo per ottenere la Lista completa degli studenti
    public List<Student> getAll() {

        // Crea lista vuota
        List<Student> studentList = new ArrayList<>();

        // Prova di connessione al database
        try (Connection conn = DBConnection.getConnection()) {

            // Query da eseguire
            String sql = "SELECT student_id, first_name, last_name, student_number, date_of_birth FROM Students";

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
}


    // Metodo per ottenere i dati di un solo studente tramite l'id
    /* @Override
    public Student getById(int id) {
        return students.stream()
                .filter(s -> s.getId() == id)
                .findFirst()
                .orElse(null);
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


