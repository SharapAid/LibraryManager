package DAO;

import Model.Entity.Loan;
import java.sql.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class LoanDAO {

    public void insert(Loan loan) {
        String sql = "INSERT INTO loans (book_id, client_id, loan_date, date_returned) VALUES (?, ?, ?, ?)";
        try (Connection connection = DataBaseManager.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            preparedStatement.setInt(1, loan.getBookId());
            preparedStatement.setInt(2, loan.getClientId());
            preparedStatement.setString(3, loan.getDateIssued());
            preparedStatement.setString(4,loan.getDateReturned());
            preparedStatement.executeUpdate();
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
             ResultSet resultSet = statement.executeQuery(sql)) {

            while (resultSet.next()) {
                report.add(new Object[]{
                        resultSet.getInt("loan_id"),
                        resultSet.getString("title"),
                        resultSet.getString("name"),
                        resultSet.getString("loan_date"),
                        resultSet.getString("actual_date_returned"),
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
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setString(1, loan.getDateReturned());
            preparedStatement.setInt(2, loan.getIndex());

            preparedStatement.executeUpdate();
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
            try (PreparedStatement preparedStatementSelect = connection.prepareStatement(selectBookIdSql)) {
                preparedStatementSelect.setInt(1, loanId);
                try (ResultSet resultSet = preparedStatementSelect.executeQuery()) {
                    if (resultSet.next()) {
                        bookId = resultSet.getInt("book_id");
                    }
                }
            }

            if (bookId != -1) {
                try (PreparedStatement preparedStatementLoan = connection.prepareStatement(updateLoanSql);
                     PreparedStatement preparedStatementBook = connection.prepareStatement(updateBookSql)) {

                    String currentDate = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));

                    preparedStatementLoan.setString(1, currentDate);
                    preparedStatementLoan.setInt(2, loanId);
                    preparedStatementLoan.executeUpdate();

                    preparedStatementBook.setInt(1, bookId);
                    preparedStatementBook.executeUpdate();

                    connection.commit();
                }
                catch (SQLException e) {
                    connection.rollback();
                    throw e;
                }
            }
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
    }
}