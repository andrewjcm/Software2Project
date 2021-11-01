package dao;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.query.ContactSchedule;
import model.query.Count;
import utils.time.ZoneLocalize;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;

public class Query {


    private static ObservableList<ContactSchedule> allContactSchedules = FXCollections.observableArrayList();
    private static ObservableList<Count> allMonthCounts = FXCollections.observableArrayList();
    private static ObservableList<Count> allTypeCounts = FXCollections.observableArrayList();
    private static ObservableList<Count> allDivisionCounts = FXCollections.observableArrayList();


    private static ContactSchedule createContactScheduleObj(ResultSet rs) throws SQLException {
        String contactName = rs.getString("Contact_Name");
        int apptId = rs.getInt("Appointment_ID");
        String title = rs.getString("Title");
        String desc = rs.getString("Description");
        Timestamp start = rs.getTimestamp("Start");
        Timestamp end = rs.getTimestamp("End");
        int customerId = rs.getInt("Customer_ID");

        return new ContactSchedule(
                contactName, apptId, title, desc, ZoneLocalize.toSysDefault(start),
                ZoneLocalize.toSysDefault(end), customerId
        );
    }

    private static Count createCountObj(ResultSet rs) throws SQLException {
        String string = rs.getString("Str");
        int count = rs.getInt("Count");

        return new Count(string, count);
    }

    public static ObservableList<ContactSchedule> getAllContactSchedules() {

        if (allContactSchedules.isEmpty()) {
            try {
                String sql = "SELECT Contact_Name, Appointment_ID, Title, Description, Start, End, Customer_ID\n" +
                        "From Contacts\n" +
                        "JOIN Appointments\n" +
                        "WHERE Contacts.Contact_ID = Appointments.Contact_ID\n" +
                        "ORDER BY Contact_Name, Start";
                PreparedStatement ps = DBConnection.getConnection().prepareStatement(sql);
                ResultSet rs = ps.executeQuery();

                while (rs.next()) {
                    allContactSchedules.add(createContactScheduleObj(rs));
                }

            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return allContactSchedules;
    }

    public static ObservableList<Count> getAllMonthCounts() {

        if (allMonthCounts.isEmpty()) {
            try {
                String sql = "SELECT date_format(Start, '%M %Y') as Str, COUNT(MONTH(Start)) as 'Count'\n" +
                        "FROM appointments\n" +
                        "GROUP BY MONTH(Start)\n";
                PreparedStatement ps = DBConnection.getConnection().prepareStatement(sql);
                ResultSet rs = ps.executeQuery();

                while (rs.next()) {
                    allMonthCounts.add(createCountObj(rs));
                }

            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return allMonthCounts;
    }

    public static ObservableList<Count> getAllTypeCounts() {

        if (allTypeCounts.isEmpty()) {
            try {
                String sql = "SELECT Type as Str, COUNT(Type) as 'Count'\n" +
                        "FROM appointments\n" +
                        "GROUP BY Type";
                PreparedStatement ps = DBConnection.getConnection().prepareStatement(sql);
                ResultSet rs = ps.executeQuery();

                while (rs.next()) {
                    allTypeCounts.add(createCountObj(rs));
                }

            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return allTypeCounts;
    }


    public static ObservableList<Count> getAllDivisionCounts() {

        if (allDivisionCounts.isEmpty()) {
            try {
                String sql = "SELECT Division as Str, COUNT(appointments.Customer_ID) as 'Count'\n" +
                        "FROM first_level_divisions\n" +
                        "JOIN customers\n" +
                        "ON first_level_divisions.Division_ID = customers.Division_ID\n" +
                        "JOIN appointments\n" +
                        "ON customers.Customer_ID = appointments.Customer_ID\n" +
                        "GROUP BY Division";
                PreparedStatement ps = DBConnection.getConnection().prepareStatement(sql);
                ResultSet rs = ps.executeQuery();

                while (rs.next()) {
                    allDivisionCounts.add(createCountObj(rs));
                }

            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return allDivisionCounts;
    }
}
