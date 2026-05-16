package DAO;

import Model.Entity.Book;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DataBaseManager {
    private static final String URL = "jdbc:sqlite:library.db";

    public static Connection getConnection() throws SQLException{
        return DriverManager.getConnection(URL);
    }

    public static void initialize() {
        try (Connection connection = getConnection();
             Statement statement = connection.createStatement()) {

            statement.execute("""
                CREATE TABLE IF NOT EXISTS books (
                    id INTEGER PRIMARY KEY AUTOINCREMENT,
                    title TEXT NOT NULL,
                    author TEXT NOT NULL,
                    genre TEXT NOT NULL,
                    status INTEGER DEFAULT 1
                );
            """);

            statement.execute("""
                CREATE TABLE IF NOT EXISTS clients (
                    id INTEGER PRIMARY KEY AUTOINCREMENT,
                    name TEXT NOT NULL,
                    phone TEXT,
                    email TEXT,
                    address TEXT NOT NULL
                );
            """);

            statement.execute("""
                CREATE TABLE IF NOT EXISTS loans (
                    id INTEGER PRIMARY KEY AUTOINCREMENT,
                    book_id INTEGER,
                    client_id INTEGER,
                    loan_date TEXT,
                    date_returned,
                    FOREIGN KEY (book_id) REFERENCES books(id),
                    FOREIGN KEY (client_id) REFERENCES clients(id)
                );
            """);

            System.out.println("Data base is successfully initialize!");
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
