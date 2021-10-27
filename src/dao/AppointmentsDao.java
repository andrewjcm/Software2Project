package dao;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Appointment;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;

public class AppointmentsDao {

    private static Appointment createAppointmentObj(ResultSet rs) {
        try {
            int id = rs.getInt("Appointment_ID");
            String title = rs.getString("Title");
            String desc = rs.getString("Description");
            String loc = rs.getString("Location");
            String type = rs.getString("Type");
            Timestamp start = rs.getTimestamp("Start");
            Timestamp end = rs.getTimestamp("End");
            Timestamp createDate = rs.getTimestamp("Create_Date");
            String createdBy = rs.getString("Created_By");
            Timestamp updateDate = rs.getTimestamp("Last_Update");
            String updatedBy = rs.getString("Last_Updated_By");
            int customer_id = rs.getInt("Customer_ID");
            int user_id = rs.getInt("User_ID");
            int contact_id = rs.getInt("Contact_ID");

            // TODO: Fetch individual Customer, User, and Contact Object.
            return new Appointment(
                    id, title, desc, loc, type, start, end,
                    createDate, createdBy, updateDate, updatedBy,
                    customer_id, user_id, contact_id);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static ObservableList<Appointment> getAllAppointments() {
        ObservableList<Appointment> allAppointments = FXCollections.observableArrayList();

        try {
            String sql = "SELECT * FROM Appointments";
            PreparedStatement ps = DBConnection.getConnection().prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            while (rs.next()){
                allAppointments.add(createAppointmentObj(rs));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return allAppointments;
    }
}
