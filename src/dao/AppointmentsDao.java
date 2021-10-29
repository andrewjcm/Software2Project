package dao;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Appointment;
import model.Contact;
import model.Customer;
import model.User;
import utils.Time.TZConverter;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;

public class AppointmentsDao {

    private static ObservableList<Appointment> allAppointments = FXCollections.observableArrayList();
    private static int apptId = -1;

    public static int getIncrementedApptId() {
        if (apptId == -1){
            int lastApptIndex = allAppointments.size()-1;
            apptId = allAppointments.get(lastApptIndex).getId();
        }

        return apptId++;
    }

    public static ObservableList<Appointment> filterByDateRange(LocalDateTime start, LocalDateTime end){
        ObservableList<Appointment> filteredAppts = FXCollections.observableArrayList();

        for (Appointment appt: AppointmentsDao.getAllAppointments()){
            if (appt.getStart().isAfter(start) && appt.getStart().isBefore(end))
                filteredAppts.add(appt);
        }
        return filteredAppts;
    }

    private static Appointment createAppointmentObj(ResultSet rs) throws SQLException {
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

        // Three lambda functions to locate the Customer, User and Contact objects in an ObservableList by ID.
        Customer customer = CustomersDao.getAllCustomers().stream().filter(
                cust -> cust.getId() == customer_id
        ).findFirst().orElse(null);

        User user = UsersDoa.getAllUsers().stream().filter(
                u -> u.getId() == user_id
        ).findFirst().orElse(null);

        Contact contact = ContactsDao.getAllContacts().stream().filter(
                cont -> cont.getId() == contact_id
        ).findFirst().orElse(null);

        return new Appointment(
                id, title, desc, loc, type, TZConverter.fromDb(start), TZConverter.fromDb(end),
                TZConverter.fromDb(createDate), createdBy, TZConverter.fromDb(updateDate), updatedBy,
                customer, user, contact);
    }

    public static ObservableList<Appointment> getAllAppointments() {

        if (allAppointments.size() == 0) {
            try {
                String sql = "SELECT * FROM Appointments ORDER BY Appointment_ID";
                PreparedStatement ps = DBConnection.getConnection().prepareStatement(sql);
                ResultSet rs = ps.executeQuery();

                while (rs.next()) {
                    allAppointments.add(createAppointmentObj(rs));
                }

            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return allAppointments;
    }

    public static void addAppointment(Appointment appt) throws SQLException {

        String sql = "INSERT INTO Appointments (" +
                "Appointment_ID, Title, Description," +
                " Location, Type, Start, End, Create_Date," +
                " Created_By, Last_Update, Last_Updated_By," +
                " Customer_ID, User_ID, Contact_ID)" +
                " values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        PreparedStatement ps = DBConnection.getConnection().prepareStatement(sql);

        ps.setInt(1, appt.getId());
        ps.setString(2, appt.getTitle());
        ps.setString(3, appt.getDescription());
        ps.setString(4, appt.getLocation());
        ps.setString(5, appt.getType());
        ps.setTimestamp(6, TZConverter.toDb(appt.getStart()));
        ps.setTimestamp(7, TZConverter.toDb(appt.getEnd()));
        ps.setTimestamp(8, TZConverter.toDb(appt.getCreateDate()));
        ps.setString(9, appt.getCreatedBy());
        ps.setTimestamp(10, TZConverter.toDb(appt.getLastUpdate()));
        ps.setString(11, appt.getUpdatedBy());
        ps.setInt(12, appt.getCustomer().getId());
        ps.setInt(13, appt.getUser().getId());
        ps.setInt(14, appt.getContact().getId());

        ps.execute();

        // add to ram list
        allAppointments.add(appt);
    }

    public static void deleteAppointment(Appointment appt) throws SQLException {

        String sql = "DELETE FROM Appointments WHERE Appointment_ID=" + appt.getId();

        PreparedStatement ps = DBConnection.getConnection().prepareStatement(sql);

        ps.execute();

        // Locate in RAM list and remove

        if (allAppointments.size() > 0)
            allAppointments.remove(appt);
    }
}
