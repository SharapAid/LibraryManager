package DAO;

import Model.Entity.Book;
import Model.Entity.Loan;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class LoanDAO {

    public void insert(Loan loan) {
        String sql = "INSERT INTO loans (book_id, client_id, loan_date, date_returned) VALUES (?, ?, ?, ?)";
        try (Connection connection = DataBaseManager.getConnection();
             PreparedStatement pstmt = connection.prepareStatement(sql)) {

            pstmt.setInt(1, loan.getBookId());
            pstmt.setInt(2, loan.getClientId());
            pstmt.setString(3, loan.getDateIssued());
            pstmt.setString(4,loan.getDateReturned());
            pstmt.executeUpdate();

            System.out.println("Writing about loan created!");
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<Object[]> getAllDetailed() {
        List<Object[]> report = new ArrayList<>();

        String sql = """
            SELECT b.title, c.name, l.loan_date, 
                   COALESCE(l.date_returned, 'Not returned') as actual_date_returned, 
                   b.status 
            FROM loans l
            JOIN books b ON l.book_id = b.id
            JOIN clients c ON l.client_id = c.id
        """;

        try (Connection connection = DataBaseManager.getConnection();
             Statement statement = connection.createStatement();
             ResultSet rs = statement.executeQuery(sql)) {

            while (rs.next()) {
                boolean status = rs.getBoolean("status");

                report.add(new Object[]{
                        rs.getString("title"),
                        rs.getString("name"),
                        rs.getString("loan_date"),
                        rs.getString("actual_date_returned"),
                        status
                });
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return report;
    }

    public static void update(Loan loan) {
        String query = "UPDATE loans SET date_returned = ? WHERE id = ?";

        try (Connection connection = DataBaseManager.getConnection();
             PreparedStatement stmt = connection.prepareStatement(query)) {

            stmt.setString(1, loan.getDateReturned());
            stmt.setInt(2, loan.getIndex());

            stmt.executeUpdate();
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void returnBookByTitle(String bookTitle, String returnDate) {
        String updateLoanSql = "UPDATE loans SET date_returned = ? " +
                "WHERE book_id = (SELECT id FROM books WHERE title = ? LIMIT 1) " +
                "AND date_returned IS NULL";

        String updateBookSql = "UPDATE books SET status = 1 WHERE title = ?";

        try (Connection connection = DataBaseManager.getConnection()) {
            connection.setAutoCommit(false);

            try (PreparedStatement stmtLoan = connection.prepareStatement(updateLoanSql);
                 PreparedStatement stmtBook = connection.prepareStatement(updateBookSql)) {

                stmtLoan.setString(1, returnDate);
                stmtLoan.setString(2, bookTitle);
                stmtLoan.executeUpdate();

                stmtBook.setString(1, bookTitle);
                stmtBook.executeUpdate();

                connection.commit();
            }
            catch (SQLException e) {
                connection.rollback();
                throw e;
            }
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
    }
}