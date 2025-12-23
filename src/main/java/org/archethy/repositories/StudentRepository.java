package org.archethy.repositories;

import org.archethy.models.Student;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Repository
public class StudentRepository implements IRepositoryRead<Student>, IRepositoryWrite<Student> {


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
                student.setStudentId((rs.getInt("student_id")));
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

            // Esegui la query
            ResultSet rs = ps.executeQuery();

            // Continua a leggere finché esiste un record successivo all'interno del database
            while (rs.next()) {
                Student student = new Student();
                student.setStudentId((rs.getInt("student_id")));
                student.setFirstname(rs.getString("first_name"));
                student.setLastname(rs.getString("last_name"));
                student.setStudentNumber(rs.getString("student_number"));
                student.setDateOfBirth(rs.getDate("date_of_birth").toLocalDate());

                // Restituisci il singolo studente
                return student;
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        // Se la lettura non va a buon fine
        return null;
    }


    // Metodo per inserire un nuovo oggetto Studente
    @Override
    public boolean insert(Student obj) {

        // Prova di connessione al database
        try (Connection conn = DBConnection.getConnection()) {

            // Query da eseguire. Inserisco inizialmente delle wildcard come valori. Non inserisco id.
            String sql = "INSERT INTO student (first_name, last_name, student_number, date_of_birth)" +
                    "VALUES (?, ?, ?, ?)";

            // Metodo anti SQL Injection
            PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

            /* Setta i valori delle singole colonne come proprietà di un generico oggetto obj ad esclusione dell'id
            (è un auto-increment generato dal db, va recuperato dopo). I metodi vengono presi dal Model. */
            ps.setString(1, obj.getFirstname());
            ps.setString(2, obj.getLastname());
            ps.setString(3, obj.getStudentNumber());
            ps.setDate(4, Date.valueOf(obj.getDateOfBirth()));

            /* Aggiorna il database eseguendo l'insert e assegna il valore numerico che questo metodo restituisce alla variabile
            affectedRows (= righe coinvolte nell'aggiornamento del database') */
            int affectedRows = ps.executeUpdate();

            // Recupera l'id generato dal DB con il metodo ps.getGeratedKeys e impostalo
            try (ResultSet keys = ps.getGeneratedKeys()) {
                if (keys.next()) {
                    obj.setStudentId(keys.getInt(1));
                }
            }

            // Se il numero di righe modificate è > 0, significa che l'operazione è andata a buon fine
            if (affectedRows > 0) {
                return true;
            }

        } catch (SQLException e) {
            System.err.println("Errore nella query: " + e.getErrorCode() + " " + e.getMessage());
            return false;
        }

        return false;
    }


    @Override
    public boolean update(Student obj) {

        return false;
    }

    @Override
    public boolean delete(int id) {
        return false;
    }
}





