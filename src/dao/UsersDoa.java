package dao;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.User;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;

public class UsersDoa {
    private static User createUserObj(ResultSet rs) throws SQLException {
        int id = rs.getInt("User_ID");
        String username = rs.getString("User_Name");
        String password = rs.getString("Password");
        Timestamp createDate = rs.getTimestamp("Create_Date");
        String createdBy = rs.getString("Created_By");
        Timestamp updateDate = rs.getTimestamp("Last_Update");
        String updatedBy = rs.getString("Last_Updated_By");


        return new User(id, username, password, createDate, createdBy, updateDate, updatedBy);
    }

    public static ObservableList<User> getAllUsers() {
        ObservableList<User> allUsers = FXCollections.observableArrayList();

        try {
            String sql = "SELECT * FROM Users";

            PreparedStatement ps = DBConnection.getConnection().prepareStatement(sql);

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                allUsers.add(createUserObj(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return allUsers;
    }

    public static User getUser(int id) throws SQLException {
        String sql = "SELECT * FROM Users WHERE User_ID=" + id;
        PreparedStatement ps = DBConnection.getConnection().prepareStatement(sql);
        ResultSet rs = ps.executeQuery();
        if (rs.next())
            return createUserObj(rs);
        else
            return null;
    }
}
