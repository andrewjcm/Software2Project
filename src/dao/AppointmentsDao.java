package dao;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Appointment;
import model.Contact;
import model.Customer;
import model.User;
import utils.time.ZoneLocalize;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

public class AppointmentsDao {

    private static ObservableList<Appointment> allAppointments = FXCollections.observableArrayList();
    private static int apptId = -1;

    public static int getIncrementedApptId() {
        if (apptId == -1){
            int lastApptIndex = allAppointments.size()-1;
            apptId = allAppointments.get(lastApptIndex).getId();
        }

        return ++apptId;
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
                id, title, desc, loc, type, ZoneLocalize.toSysDefault(start), ZoneLocalize.toSysDefault(end),
                ZoneLocalize.toSysDefault(createDate), createdBy, ZoneLocalize.toSysDefault(updateDate), updatedBy,
                customer, user, contact);
    }

    public static ObservableList<Appointment> getAllAppointments() {

        if (allAppointments.isEmpty()) {
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
                "Title, Description, Location, Type, Start, End, Create_Date," +
                " Created_By, Last_Update, Last_Updated_By, Customer_ID, User_ID, Contact_ID)" +
                " values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        PreparedStatement ps = DBConnection.getConnection().prepareStatement(sql);

        ps.setString(1, appt.getTitle());
        ps.setString(2, appt.getDescription());
        ps.setString(3, appt.getLocation());
        ps.setString(4, appt.getType());
        ps.setTimestamp(5, ZoneLocalize.toDb(appt.getStart()));
        ps.setTimestamp(6, ZoneLocalize.toDb(appt.getEnd()));
        ps.setTimestamp(7, ZoneLocalize.toDb(appt.getCreateDate()));
        ps.setString(8, appt.getCreatedBy());
        ps.setTimestamp(9, ZoneLocalize.toDb(appt.getLastUpdate()));
        ps.setString(10, appt.getUpdatedBy());
        ps.setInt(11, appt.getCustomer().getId());
        ps.setInt(12, appt.getUser().getId());
        ps.setInt(13, appt.getContact().getId());

        ps.execute();

        // add to ram list
        apptId = getLastAddedAppt().getId();
        appt.setId(apptId);
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

    public static void updateAppointment(int apptIndex, Appointment origAppt, Appointment newAppt) throws SQLException {
        String sql = "UPDATE Appointments SET" +
                " Title = ?," +
                " Description = ?," +
                " Location = ?," +
                " Type = ?," +
                " Start = ?," +
                " End = ?," +
                " Last_Update = ?," +
                " Last_Updated_By = ?," +
                " Customer_ID = ?," +
                " User_ID = ?," +
                " Contact_ID = ?" +
                " WHERE Appointment_ID = ?";

        PreparedStatement ps = DBConnection.getConnection().prepareStatement(sql);

        ps.setString(1, newAppt.getTitle());
        ps.setString(2, newAppt.getDescription());
        ps.setString(3, newAppt.getLocation());
        ps.setString(4, newAppt.getType());
        ps.setTimestamp(5, ZoneLocalize.toDb(newAppt.getStart()));
        ps.setTimestamp(6, ZoneLocalize.toDb(newAppt.getEnd()));
        ps.setTimestamp(7, ZoneLocalize.toDb(newAppt.getLastUpdate()));
        ps.setString(8, newAppt.getUpdatedBy());
        ps.setInt(9, newAppt.getCustomer().getId());
        ps.setInt(10, newAppt.getUser().getId());
        ps.setInt(11, newAppt.getContact().getId());
        ps.setInt(12, origAppt.getId());

        ps.executeUpdate();

        allAppointments.set(apptIndex, newAppt);
    }

    public static Appointment getLastAddedAppt() throws SQLException {
        String sql = "SELECT * FROM Appointments ORDER BY Appointment_ID DESC LIMIT 1";
        PreparedStatement ps = DBConnection.getConnection().prepareStatement(sql);
        ResultSet rs = ps.executeQuery();
        if (rs.next())
            return createAppointmentObj(rs);
        else
            return null;
    }

    public static boolean openAppointmentTime(LocalDateTime start, LocalDateTime end) {
        for (Appointment appt: allAppointments){
            if (appt.getStart().equals(start) ||
                    (appt.getStart().isAfter(start) && appt.getStart().isBefore(end)))
                return false;
        }
        return true;
    }
}
