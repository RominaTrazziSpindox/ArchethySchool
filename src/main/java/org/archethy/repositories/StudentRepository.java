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

        // Prova di connessione al database
        try (Connection conn = DBConnection.getConnection()) {

            // Crea lista vuota
            List<Student> studentList = new ArrayList<>();

            // Query da eseguire
            String sql = "SELECT student_id, first_name, last_name, student_number, date_of_birth FROM student";

            // Metodo anti SQL Injection
            PreparedStatement ps = conn.prepareStatement(sql);

            // Esegui la query e crea un "cursore" di tipo ResultSet
            ResultSet rs = ps.executeQuery();

            /* Continua a leggere finché esiste un record successivo all'interno del database.
            Per ogni riga crea un nuovo oggetto Student da popolare */
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

            // Restiuisci la lista di studenti letta dal db
            return studentList;

        } catch (SQLException e) {
            System.err.println("Errore " + e.getErrorCode() + ": " + e.getMessage());
        }

        return null;
    }


    // Metodo per ottenere i dati di un solo (oggetto) studente tramite l'id
    @Override
    public Student getById(int id) {

        // Prova di connessione al database
        try (Connection conn = DBConnection.getConnection()) {

            // Query SQL parametrizzata da eseguire
            // - seleziona i campi necessari dalla tabella Students dal db
            // - usa il placeholder '?' per evitare SQL Injection
            String sql = "SELECT student_id, first_name, last_name, student_number, date_of_birth FROM student WHERE student_id = ?";

            // Metodo anti SQL Injection
            PreparedStatement ps = conn.prepareStatement(sql);

            // Sostituisce il placeholder '?' con il valore dell'id passato al metodo. 1 è il numero del placeholder.
            ps.setInt(1, id);

            // Esegui la query e crea un "cursore" di tipo ResultSet
            ResultSet rs = ps.executeQuery();

            /* Se la query ha restituito una riga (ne può restituire solo una perché vengono filtrate dall'id),
            spostati su quella riga e permettimi di leggerne i dati, creando un nuovo oggetto Student da popolare */
            if (rs.next()) {
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
            System.err.println("Errore " + e.getErrorCode() + ": " + e.getMessage());
        }

        // Se la lettura non va a buon fine
        return null;
    }


    // Metodo per inserire un nuovo (oggetto) studente
    @Override
    public boolean insert(Student obj) {

        // Prova di connessione al database
        try (Connection conn = DBConnection.getConnection()) {

            // Query da eseguire. Inserisco inizialmente delle wildcard come valori. Non inserisco id.
            String sql = "INSERT INTO student (first_name, last_name, student_number, date_of_birth)" +
                    "VALUES (?, ?, ?, ?)";

            // Metodo anti SQL Injection
            PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

            /* Setta i valori delle singole colonne come proprietà di un generico oggetto obj passato come parametro,
            ad esclusione dell'id (è un auto-increment generato dal db, va recuperato dopo).
            I metodi vengono presi dal Model dell'oggetto. */
            ps.setString(1, obj.getFirstname());
            ps.setString(2, obj.getLastname());
            ps.setString(3, obj.getStudentNumber());
            ps.setDate(4, Date.valueOf(obj.getDateOfBirth()));

            /* Aggiorna il database eseguendo la query e assegna il valore numerico che questo metodo restituisce
            alla variabile affectedRows (= righe coinvolte nell'aggiornamento del database') */
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
            System.err.println("Errore " + e.getErrorCode() + ": " + e.getMessage());
        }

        return false;
    }


    // Metodo per modificare i dati di un (oggetto) studente già inserito
    @Override
    public boolean update(Student obj) {

        // Prova di connessione al database
        try (Connection conn = DBConnection.getConnection()) {

            // Estrae dall'oggetto passato come input il valore dell'id
            int id = obj.getStudentId();

            /* Sei dentro StudentRepository
            this = questa istanza della classe StudentRepository
            getById è un metodo contenuto nella classe (vedi sopra) che l'istanza può utilizzare.
            getById restituisce un singolo oggetto student se trovato grazie al parametro id, oppure null se non trovato.
            Questo oggetto student esiste già nel database. */
            Student student = this.getById(id);

            // student = oggetto già nel db
            // obj = oggetto esterno che deve modificare student

            /* Se l'oggetto studente con l'id selezionato non è nullo (e quindi esiste), allora sostituisci i valori delle
            proprietà dell'oggetto Java passato come parametro (obj) ai valori delle proprietà dell'oggetto Java
            appena trovato (student) */
            if(student != null) {
                student.setFirstname(obj.getFirstname());
                student.setLastname(obj.getLastname());
                student.setStudentNumber(obj.getStudentNumber());
                student.setDateOfBirth(obj.getDateOfBirth());

                // Query da eseguire. Inserisco inizialmente delle wildcard come valori. Non inserisco id.
                String sql = "UPDATE student SET " + "first_name = ?," + "last_name = ?," + "student_number = ?," +
                        "date_of_birth = ?" + "WHERE student_id=?";

                // Metodo anti SQL Injection
                PreparedStatement ps = conn.prepareStatement(sql);

                /* Set dei parametri: il placeholder '?' viene sostituito con il valore passato al metodo.
                I numeri 1, 2 ecc. corrispondono in ordine ai ? di String sql
                Avendo ora student i valori di obj, questi vengono impostati nella query */
                ps.setString(1, student.getFirstname());
                ps.setString(2, student.getLastname());
                ps.setString(3, student.getStudentNumber());
                ps.setDate(4, Date.valueOf(student.getDateOfBirth()));
                ps.setInt(5, student.getStudentId());

                /* Aggiorna il database eseguendo la query e assegna il valore numerico che questo metodo restituisce
                alla variabile affectedRows (= righe coinvolte nell'aggiornamento del database') */
                int affectedRows = ps.executeUpdate();

                // Se il numero di righe modificate è > 0, significa che l'operazione è andata a buon fine
                if (affectedRows > 0) {
                    return true;
                }

            }

        } catch (SQLException e) {
            System.err.println("Errore " + e.getErrorCode() + ": " + e.getMessage());
        }

        return false;
    }


     // Metodo per eliminare un (oggetto) studente tramite l'id (= riga del database)
    @Override
    public boolean delete(int id) {

        // Prova di connessione al database
        try (Connection conn = DBConnection.getConnection()) {

            // Query da eseguire. L'id è una wildcard.
            String sql = "DELETE FROM student WHERE student_id=?";

            // Metodo anti SQL Injection
            PreparedStatement ps = conn.prepareStatement(sql);

            // Selezione dell'id (1 è l'indice del wildcard, id è l'id passato come parametro)
            ps.setInt(1, id);

            /* Aggiorna il database eseguendo la query e assegna il valore numerico che questo metodo restituisce
            alla variabile affectedRows (= righe coinvolte nell'aggiornamento del database') */
            int affectedRows = ps.executeUpdate();

            // Se il numero di righe modificate è > 0, significa che l'operazione è andata a buon fine
            if (affectedRows > 0) {
                return true;
            }

        } catch (SQLException e) {
            System.err.println("Errore " + e.getErrorCode() + ": " + e.getMessage());
        }

        return false;
    }
}





