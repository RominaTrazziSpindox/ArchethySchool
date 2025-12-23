package org.archethy.repositories;

import io.github.cdimascio.dotenv.Dotenv;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {

    private static Connection connection = null;
    private static final int MAX_RETRIES = 5;       // Numero massimo di tentativi
    private static final int RETRY_DELAY_MS = 3000; // Attesa tra i tentativi (millisecondi)

    public static Connection getConnection() {

        // Se la connessione esiste già, la riusa
        if (connection != null) {
            return connection;
        }

        // Carica la libreria che legge le variabili nel file .env
        Dotenv dotenv = Dotenv.load();

        // Stringa di connessione
        String url = "jdbc:mysql://" +
                dotenv.get("DB_HOST") + ":" +
                dotenv.get("DB_PORT") + "/" +
                dotenv.get("DB_NAME") +
                "?allowPublicKeyRetrieval=true" +
                "&useSSL=false" +
                "&serverTimezone=UTC";

        String user = dotenv.get("DB_USER");
        String password = dotenv.get("DB_PASSWORD");

        int attempt = 0;

        while (attempt < MAX_RETRIES) {
            try {
                attempt++;
                System.out.println("Tentativo di connessione #" + attempt + " al database...");

                connection = DriverManager.getConnection(url, user, password);
                System.out.println("Connessione al database stabilita con successo!");
                return connection;

            } catch (SQLException e) {
                System.err.println("Errore di connessione (tentativo " + attempt + "): " + e.getErrorCode() + " " + e.getMessage());

                if (attempt < MAX_RETRIES) {
                    try {
                        System.out.println("Riprovo tra " + (RETRY_DELAY_MS / 1000) + " secondi...");
                        Thread.sleep(RETRY_DELAY_MS);
                    } catch (InterruptedException ie) {
                        Thread.currentThread().interrupt();
                        System.err.println("Retry interrotto.");
                        break;
                    }
                } else {
                    System.err.println("Raggiunto il numero massimo di tentativi (" + MAX_RETRIES + "). Riprova a connetterti.");
                }
            }
        }

        // Se tutti i tentativi falliscono
        return null;
    }

    // Metodo statico per chiudere la connessione
    public static void closeConnection() {
        if (connection != null) {
            try {
                connection.close();
                System.out.println("Connessione chiusa correttamente.");
            } catch (SQLException e) {
                System.err.println("Errore nella chiusura della connessione: " + e.getErrorCode() + " " + e.getMessage());
            }
        }
    }


}