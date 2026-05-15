package DAO;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class DataBaseManager {
    private static final String URL = "jdbc:sqlite:library.db";

    public static Connection getConnection() throws SQLException{
        return DriverManager.getConnection(URL);
    }

    public static void initialize() {
        try (Connection connection = getConnection();
             Statement statement = connection.createStatement()) {

            // Создаем таблицу книг
            statement.execute("""
                CREATE TABLE IF NOT EXISTS books (
                    id INTEGER PRIMARY KEY AUTOINCREMENT,
                    title TEXT NOT NULL,
                    author TEXT NOT NULL
                );
            """);

            System.out.println("Data base is successfully initialize!");
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
