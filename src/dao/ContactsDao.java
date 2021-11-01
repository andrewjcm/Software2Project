package dao;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Contact;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Contact data access object.
 * @author Andrew Cesar-Metzgus
 */
public class ContactsDao {
    public static ObservableList<Contact> allContacts = FXCollections.observableArrayList();

    /**
     * Creates a Contact object from a database results set.
     * @param rs Results Set
     * @return Contact object.
     * @throws SQLException
     */
    private static Contact createContactObj(ResultSet rs) throws SQLException {
        int id = rs.getInt("Contact_ID");
        String name = rs.getString("Contact_Name");
        String email = rs.getString("Email");

        return new Contact(id, name, email);
    }

    /**
     * Gets all contacts from database if not already in program memory.
     * @return ObservableList of contacts.
     */
    public static ObservableList<Contact> getAllContacts() {
        if (allContacts.isEmpty()) {
            try {
                String sql = "SELECT * FROM Contacts";

                PreparedStatement ps = DBConnection.getConnection().prepareStatement(sql);

                ResultSet rs = ps.executeQuery();

                while (rs.next()) {
                    allContacts.add(createContactObj(rs));
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return allContacts;
    }
}
