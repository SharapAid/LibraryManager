package DAO;

import Model.Entity.Book;
import Model.Entity.Client;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import static DAO.DataBaseManager.getConnection;

public class ClientDAO {

    public void insert(Client client) {
        String sql = "INSERT INTO clients (name, phone, email, address) VALUES (?, ?, ?, ?)";
        try (Connection conn = DataBaseManager.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, client.getName());
            pstmt.setString(2, client.getPhone());
            pstmt.setString(3, client.getEmail());
            pstmt.setString(4, client.getAddress());
            pstmt.executeUpdate();
            System.out.println("Client " + client.getName() + " was insert!");
        } catch (SQLException e) { e.printStackTrace(); }
    }

    public static List<Client> getAll() {
        List<Client> clients = new ArrayList<>();
        String sql = "SELECT * FROM clients";

        try (Connection conn = getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                Client client = new Client(
                        rs.getString("name"),
                        rs.getString("phone"),
                        rs.getString("email"),
                        rs.getString("address"),
                        rs.getInt("id")
                );
                clients.add(client);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return clients;
    }

}
