package dao;

import model.Contact;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ContactsDao {

}
    private static Contact createContactObj(ResultSet rs) {
        try {
            int id = rs.getInt("Contact_ID");
            String name = rs.getString("Contact_Name");
            String email = rs.getString("Email");

            return new Contact(id, name, email);
        } catch (SQLException e) {
            e.printStackTrace();
        }
}
