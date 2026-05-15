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

    public List<Loan> getAll() {
        List<Loan> loansList = new ArrayList<>();
        String sql = "SELECT * FROM loans";

        try (Connection conn = DataBaseManager.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                Loan loan = new Loan(
                        rs.getInt("book_id"),
                        rs.getInt("client_id"),
                        rs.getString("loan_date"),
                        rs.getInt("id")
                );
                loansList.add(loan);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return loansList;
    }
}