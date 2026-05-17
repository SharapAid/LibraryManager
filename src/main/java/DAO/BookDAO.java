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
             PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setString(1, book.getTitle());
            pstmt.setString(2, book.getAuthor());
            pstmt.setString(3, book.getGenre());
            pstmt.executeUpdate();
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
             ResultSet rs = statement.executeQuery(sql)) {

            while (rs.next()) {
                Book book = new Book(
                        rs.getString("title"),
                        rs.getString("author"),
                        rs.getString("genre"),
                        rs.getInt("status") == 1,
                        rs.getInt("id")
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
             PreparedStatement stmt = connection.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Book book = new Book(
                        rs.getString("title"),
                        rs.getString("author"),
                        rs.getString("genre"),
                        rs.getBoolean("status"),
                        rs.getInt("id")
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
             PreparedStatement stmt = connection.prepareStatement(query)) {

            stmt.setString(1, book.getTitle());
            stmt.setString(2, book.getAuthor());
            stmt.setString(3, book.getGenre());

            stmt.setInt(4, book.isAvailable() ? 1 : 0);
            stmt.setInt(5, book.getIndex());

            stmt.executeUpdate();
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static Book getById(int id) {
        String sql = "SELECT * FROM books WHERE id = ?";
        try (Connection connection = getConnection();
             PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return new Book(
                            rs.getString("title"), rs.getString("author"),
                            rs.getString("genre"), rs.getBoolean("status"), rs.getInt("id")
                    );
                }
            }
        } catch (SQLException e) { e.printStackTrace(); }
        return null;
    }

    public static boolean isCurrentlyBorrowed(int bookId) {
        String sql = "SELECT COUNT(*) FROM loans WHERE book_id = ? AND date_returned IS NULL";
        try (Connection connection = DataBaseManager.getConnection();
             PreparedStatement pstmt = connection.prepareStatement(sql)) {

            pstmt.setInt(1, bookId);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1) > 0;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public static void delete(int bookId) {
        String sql = "DELETE FROM books WHERE id = ?";
        try (Connection connection = DataBaseManager.getConnection();
             PreparedStatement pstmt = connection.prepareStatement(sql)) {

            pstmt.setInt(1, bookId);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
