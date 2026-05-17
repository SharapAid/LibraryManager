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
            SELECT l.id as loan_id, b.title, c.name, l.loan_date,
                   COALESCE(l.date_returned, 'Not returned') as actual_date_returned
            FROM loans l
            JOIN books b ON l.book_id = b.id
            JOIN clients c ON l.client_id = c.id
        """;

        try (Connection connection = DataBaseManager.getConnection();
             Statement statement = connection.createStatement();
             ResultSet rs = statement.executeQuery(sql)) {

            while (rs.next()) {
                report.add(new Object[]{
                        rs.getInt("loan_id"),
                        rs.getString("title"),
                        rs.getString("name"),
                        rs.getString("loan_date"),
                        rs.getString("actual_date_returned"),
                });
            }
        }
        catch (SQLException e) {
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

    public static void returnBookByLoanId(int loanId) {
        String selectBookIdSql = "SELECT book_id FROM loans WHERE id = ?";

        String updateLoanSql = "UPDATE loans SET date_returned = ? WHERE id = ?";

        String updateBookSql = "UPDATE books SET status = 1 WHERE id = ?";

        try (Connection connection = DataBaseManager.getConnection()) {
            connection.setAutoCommit(false);

            int bookId = -1;
            try (PreparedStatement stmtSelect = connection.prepareStatement(selectBookIdSql)) {
                stmtSelect.setInt(1, loanId);
                try (ResultSet rs = stmtSelect.executeQuery()) {
                    if (rs.next()) {
                        bookId = rs.getInt("book_id");
                    }
                }
            }

            if (bookId != -1) {
                try (PreparedStatement stmtLoan = connection.prepareStatement(updateLoanSql);
                     PreparedStatement stmtBook = connection.prepareStatement(updateBookSql)) {

                    String currentDate = java.time.LocalDate.now().format(java.time.format.DateTimeFormatter.ofPattern("yyyy-MM-dd"));

                    stmtLoan.setString(1, currentDate);
                    stmtLoan.setInt(2, loanId);
                    stmtLoan.executeUpdate();

                    stmtBook.setInt(1, bookId);
                    stmtBook.executeUpdate();

                    connection.commit();
                } catch (SQLException e) {
                    connection.rollback();
                    throw e;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}