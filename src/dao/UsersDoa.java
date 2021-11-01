package dao;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.User;
import utils.time.ZoneLocalize;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;

/**
 * User data access object.
 * @author Andrew Cesar-Metzgus
 */
public class UsersDoa {
    public static ObservableList<User> allUsers = FXCollections.observableArrayList();

    /**
     * Creates a User object from a database results set.
     * @param rs Results Set
     * @return User object.
     * @throws SQLException
     */
    private static User createUserObj(ResultSet rs) throws SQLException {
        int id = rs.getInt("User_ID");
        String username = rs.getString("User_Name");
        String password = rs.getString("Password");
        Timestamp createDate = rs.getTimestamp("Create_Date");
        String createdBy = rs.getString("Created_By");
        Timestamp updateDate = rs.getTimestamp("Last_Update");
        String updatedBy = rs.getString("Last_Updated_By");


        return new User(
                id, username, password, ZoneLocalize.toSysDefault(createDate),
                createdBy, ZoneLocalize.toSysDefault(updateDate), updatedBy
        );
    }

    /**
     * Gets all users from database if not already in program memory.
     * @return ObservableList of users.
     */
    public static ObservableList<User> getAllUsers() {
        if (allUsers.isEmpty()) {
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
        }

        return allUsers;
    }
}
