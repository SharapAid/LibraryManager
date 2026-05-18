package DAO;

import Model.Entity.Book;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import static DAO.DataBaseManager.getConnection;

public class BookDAO {

    public void insert(Book book) {
        String sql = "INSERT INTO books (title, author, genre) VALUES (?, ?, ?)";
        try (Connection connection = DataBaseManager.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, book.getTitle());
            preparedStatement.setString(2, book.getAuthor());
            preparedStatement.setString(3, book.getGenre());
            preparedStatement.executeUpdate();
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static List<Book> getAll() {
        List<Book> books = new ArrayList<>();
        String sql = "SELECT * FROM books";

        try (Connection connection = getConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(sql)) {

            while (resultSet.next()) {
                Book book = new Book(
                        resultSet.getString("title"),
                        resultSet.getString("author"),
                        resultSet.getString("genre"),
                        resultSet.getInt("status") == 1,
                        resultSet.getInt("id")
                );
                books.add(book);
            }
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        return books;
    }

    public static List<Book> getAllAvailable() {
        List<Book> availableBooks = new ArrayList<>();
        String query = "SELECT * FROM books WHERE status = 1";

        try (Connection connection  = DataBaseManager.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query);
             ResultSet resultSet = preparedStatement.executeQuery()) {

            while (resultSet.next()) {
                Book book = new Book(
                        resultSet.getString("title"),
                        resultSet.getString("author"),
                        resultSet.getString("genre"),
                        resultSet.getBoolean("status"),
                        resultSet.getInt("id")
                );
                availableBooks.add(book);
            }
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        return availableBooks;
    }

    public static void update(Book book) {
        String query = "UPDATE books SET title = ?, author = ?, genre = ?, status = ? WHERE id = ?";

        try (Connection connection = DataBaseManager.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setString(1, book.getTitle());
            statement.setString(2, book.getAuthor());
            statement.setString(3, book.getGenre());

            statement.setInt(4, book.isAvailable() ? 1 : 0);
            statement.setInt(5, book.getIndex());

            statement.executeUpdate();
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static Book getById(int id) {
        String sql = "SELECT * FROM books WHERE id = ?";
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, id);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    return new Book(
                            resultSet.getString("title"), resultSet.getString("author"),
                            resultSet.getString("genre"), resultSet.getBoolean("status"), resultSet.getInt("id")
                    );
                }
            }
        } catch (SQLException e) { e.printStackTrace(); }
        return null;
    }

    public static boolean isCurrentlyBorrowed(int bookId) {
        String sql = "SELECT COUNT(*) FROM loans WHERE book_id = ? AND date_returned IS NULL";
        try (Connection connection = DataBaseManager.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            preparedStatement.setInt(1, bookId);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    return resultSet.getInt(1) > 0;
                }
            }
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public static void delete(int bookId) {
        String sql = "DELETE FROM books WHERE id = ?";
        try (Connection connection = DataBaseManager.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            preparedStatement.setInt(1, bookId);
            preparedStatement.executeUpdate();
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
