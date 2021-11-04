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
import java.time.LocalDateTime;

/**
 * Database access object for User model.
 * @author Andrew Cesar-Metzgus
 */
public class AppointmentsDao {

    private static ObservableList<Appointment> allAppointments = FXCollections.observableArrayList();
    private static int apptId = -1;

    /**
     * Gets next id.
     * @return int Next ID.
     */
    public static int getIncrementedApptId() {
        if (apptId == -1){
            int lastApptIndex = allAppointments.size()-1;
            apptId = allAppointments.get(lastApptIndex).getId();
        }

        return ++apptId;
    }

    /**
     * Filters list of appointments by date,
     * @param start LocalDateTime start.
     * @param end LocalDateTime end.
     * @return ObservableList of Appointments.
     */
    public static ObservableList<Appointment> filterByDateRange(LocalDateTime start, LocalDateTime end){
        ObservableList<Appointment> filteredAppts = FXCollections.observableArrayList();

        for (Appointment appt: AppointmentsDao.getAllAppointments()){
            if (appt.getStart().isAfter(start) && appt.getStart().isBefore(end))
                filteredAppts.add(appt);
        }
        return filteredAppts;
    }

    /**
     * Creates an Appointment object from a database results set.
     * <p><br>
     * <b>Lambda Use Justification</b>
     *  In order to create an Appointment Java class object within the program, it
     *  it must contain a Customer object. This is part of the relational database.
     *  The lambda function queries the Customer DOA to find the related object
     *  more effectively using a stream and filter, rather than a for loop.
     *  </p>
     * <b>Lambda Use Justification</b>
     *  In order to create an Appointment Java class object within the program, it
     *  it must contain a User object. This is part of the relational database.
     *  The lambda function queries the User DOA to find the related object
     *  more effectively using a stream and filter, rather than a for loop.
     *  </p>
     * <b>Lambda Use Justification</b>
     *  In order to create an Appointment Java class object within the program, it
     *  it must contain a Contact object. This is part of the relational database.
     *  The lambda function queries the Contact DOA to find the related object
     *  more effectively using a stream and filter, rather than a for loop.
     *  </p>
     * @param rs Results Set
     * @return Appointment object.
     * @throws SQLException
     */
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

    /**
     * Gets all appointments from the database if does not already exist in program memory.
     * @return ObservableList of Appointments.
     */
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

    /**
     * Adds appointment to database and program memory,
     * @param appt Appointment object.
     * @throws SQLException
     */
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

        // Add to ram list
        apptId = getLastAddedAppt().getId();
        appt.setId(apptId);
        allAppointments.add(appt);
    }

    /**
     * Deletes appointment from database and program memory.
     * @param appt Appointment object.
     * @throws SQLException
     */
    public static void deleteAppointment(Appointment appt) throws SQLException {

        String sql = "DELETE FROM Appointments WHERE Appointment_ID=?";

        PreparedStatement ps = DBConnection.getConnection().prepareStatement(sql);
        ps.setInt(1, appt.getId());

        ps.execute();

        // Locate in RAM list and remove

        if (allAppointments.size() > 0)
            allAppointments.remove(appt);
    }

    /**
     * Updates appointment from database and program memory.
     * @param apptIndex int appointment index in program memory list.
     * @param origAppt Original appointment object.
     * @param newAppt New appointment object.
     * @throws SQLException
     */
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

    /**
     * Get's the last added appointment from the database.
     * @return Appointment object or null.
     * @throws SQLException
     */
    public static Appointment getLastAddedAppt() throws SQLException {
        String sql = "SELECT * FROM Appointments ORDER BY Appointment_ID DESC LIMIT 1";
        PreparedStatement ps = DBConnection.getConnection().prepareStatement(sql);
        ResultSet rs = ps.executeQuery();
        if (rs.next())
            return createAppointmentObj(rs);
        else
            return null;
    }

    /**
     * Logic: checks if appointment time is available.
     * @param start LocalDateTime start time
     * @param end LocalDateTime end time
     * @return
     */
    public static boolean openAppointmentTime(LocalDateTime start, LocalDateTime end) {
        for (Appointment appt: allAppointments){
            if (appt.getStart().equals(start) ||
                    (appt.getStart().isAfter(start) && appt.getStart().isBefore(end)))
                return false;
        }
        return true;
    }

    /**
     * Logic: checks if appointment time is available.
     * @param apptToMod Appoitnment to modify.
     * @param start LocalDateTime start time
     * @param end LocalDateTime end time
     * @return
     */
    public static boolean openAppointmentTime(Appointment apptToMod, LocalDateTime start, LocalDateTime end) {
        for (Appointment appt: allAppointments){
            if (!appt.equals(apptToMod) && appt.getStart().equals(start) ||
                    (appt.getStart().isAfter(start) && appt.getStart().isBefore(end)))
                return false;
        }
        return true;
    }
}
