package DAO;

import Model.Entity.Loan;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class LoanDAO {

    public void insert(Loan loan) {
        String sql = "INSERT INTO loans (book_id, client_id, loan_date) VALUES (?, ?, ?)";
        try (Connection conn = DataBaseManager.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, loan.getBookId());
            pstmt.setInt(2, loan.getClientId());
            pstmt.setString(3, loan.getDateIssued());
            pstmt.executeUpdate();

            System.out.println("Writing about loan created!");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<Object[]> getAllDetailed() {
        List<Object[]> report = new ArrayList<>();

        String sql = """
        SELECT b.title, c.name, l.loan_date, 'Not returned' as return_date, b.status 
        FROM loans l
        JOIN books b ON l.book_id = b.id
        JOIN clients c ON l.client_id = c.id
    """;

        try (Connection conn = DataBaseManager.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                String status = rs.getInt("status") == 1 ? "Available" : "Borrowed";

                report.add(new Object[]{
                        rs.getString("title"),
                        rs.getString("name"),
                        rs.getString("loan_date"),
                        rs.getString("return_date"),
                        status
                });
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return report;
    }
}