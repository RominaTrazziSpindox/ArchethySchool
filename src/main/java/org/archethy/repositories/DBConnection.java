package org.archethy.repositories;

import io.github.cdimascio.dotenv.Dotenv;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {

    private static final int MAX_RETRIES = 5;
    private static final int RETRY_DELAY_MS = 3000;

    public static Connection getConnection() {

        Dotenv dotenv = Dotenv.load();

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
                return DriverManager.getConnection(url, user, password);
            } catch (SQLException e) {
                if (attempt >= MAX_RETRIES) {
                    throw new RuntimeException("Connessione DB fallita", e);
                }
                try {
                    Thread.sleep(RETRY_DELAY_MS);
                } catch (InterruptedException ie) {
                    Thread.currentThread().interrupt();
                    throw new RuntimeException("Retry interrotto", ie);
                }
            }
        }

        throw new IllegalStateException("Impossibile ottenere connessione");
    }

}