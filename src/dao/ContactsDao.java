package dao;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Contact;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ContactsDao {
    public static ObservableList<Contact> allContacts = FXCollections.observableArrayList();

    private static Contact createContactObj(ResultSet rs) throws SQLException {
        int id = rs.getInt("Contact_ID");
        String name = rs.getString("Contact_Name");
        String email = rs.getString("Email");

        return new Contact(id, name, email);
    }

    public static ObservableList<Contact> getAllContacts() {
        if (allContacts.size() == 0) {
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

    public static Contact getContact(int id) throws SQLException {
        String sql = "SELECT * FROM Contacts WHERE Contact_ID=" + id;
        PreparedStatement ps = DBConnection.getConnection().prepareStatement(sql);
        ResultSet rs = ps.executeQuery();
        if (rs.next())
            return createContactObj(rs);
        else
            return null;
    }
}
