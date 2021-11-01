package dao;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Appointment;
import model.Customer;
import model.Division;
import utils.time.ZoneLocalize;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;

public class CustomersDao {

    private static ObservableList<Customer> allCustomers = FXCollections.observableArrayList();
    private static int custId = -1;

    public static int getIncrementedCustId() {
        if (custId == -1){
            int lastApptIndex = allCustomers.size()-1;
            custId = allCustomers.get(lastApptIndex).getId();
        }

        return ++custId;
    }

    private static Customer createCustomerObj(ResultSet rs) throws SQLException {
            int id = rs.getInt("Customer_ID");
            String name = rs.getString("Customer_Name");
            String address = rs.getString("Address");
            String zip = rs.getString("Postal_Code");
            String phone = rs.getString("Phone");
            Timestamp createDate = rs.getTimestamp("Create_Date");
            String createdBy = rs.getString("Created_By");
            Timestamp updateDate = rs.getTimestamp("Last_Update");
            String updatedBy = rs.getString("Last_Updated_By");
            int division_id = rs.getInt("Division_ID");

            // Lambda function to locate the Division object in an ObservableList by ID.
            Division division = DivisionsDao.getAllDivisions().stream().filter(
                    d -> d.getId() == division_id
            ).findFirst().orElse(null);

            return new Customer(
                    id, name, address, zip, phone, ZoneLocalize.toSysDefault(createDate), createdBy,
                    ZoneLocalize.toSysDefault(updateDate), updatedBy, division
            );
    }

    public static ObservableList<Customer> getAllCustomers() {
        if (allCustomers.isEmpty()) {
            try {
                String sql = "SELECT * FROM Customers";

                PreparedStatement ps = DBConnection.getConnection().prepareStatement(sql);

                ResultSet rs = ps.executeQuery();

                while (rs.next()) {
                    allCustomers.add(createCustomerObj(rs));
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return allCustomers;
    }

    public static Customer getCustomer(int id) throws SQLException {
            String sql = "SELECT * FROM Customers WHERE Customer_ID=" + id;
            PreparedStatement ps = DBConnection.getConnection().prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            if (rs.next())
                return createCustomerObj(rs);
            else
                return null;
    }

    public static void addCustomer(Customer customer) throws SQLException {
        String sql = "INSERT INTO Customers ("
                + "Customer_Name, Address, Postal_Code, Phone, Create_Date,"
                + " Created_By, Last_Update, Last_Updated_By, Division_ID)"
                + " values (?, ?, ?, ?, ?, ?, ?, ?, ?)";

        PreparedStatement ps = DBConnection.getConnection().prepareStatement(sql);

        ps.setString(1, customer.getName());
        ps.setString(2, customer.getAddress());
        ps.setString(3, customer.getPostalCode());
        ps.setString(4, customer.getPhone());
        ps.setTimestamp(5, ZoneLocalize.toDb(customer.getCreateDate()));
        ps.setString(6, customer.getCreatedBy());
        ps.setTimestamp(7, ZoneLocalize.toDb(customer.getLastUpdate()));
        ps.setString(8, customer.getLastUpdatedBy());
        ps.setInt(9, customer.getDivision().getId());

        ps.execute();

        // add to ram list
        custId = getLastAddedCust().getId();
        customer.setId(custId);
        allCustomers.add(customer);
    }

    public static void deleteCustomer(Customer customer) throws SQLException {

        String sql = "DELETE FROM Appointments WHERE Customer_ID=" + customer.getId();

        PreparedStatement ps = DBConnection.getConnection().prepareStatement(sql);

        ps.execute();

        // Locate in RAM list and remove

        if (allCustomers.size() > 0)
            allCustomers.remove(customer);
    }

    public static void updateCustomer(int custIndex, Customer origCust, Customer newCust) throws SQLException {
        String sql = "UPDATE Customers SET" +
                " Customer_Name = ?," +
                " Address = ?," +
                " Postal_Code = ?," +
                " Phone = ?," +
                " Last_Update = ?," +
                " Last_Updated_By = ?," +
                " Division_ID = ?" +
                " WHERE Customer_ID = ?";

        PreparedStatement ps = DBConnection.getConnection().prepareStatement(sql);

        ps.setString(1, newCust.getName());
        ps.setString(2, newCust.getAddress());
        ps.setString(3, newCust.getPostalCode());
        ps.setString(4, newCust.getPhone());
        ps.setTimestamp(5, ZoneLocalize.toDb(newCust.getLastUpdate()));
        ps.setString(6, newCust.getLastUpdatedBy());
        ps.setInt(7, newCust.getDivision().getId());
        ps.setInt(8, origCust.getId());

        ps.executeUpdate();

        allCustomers.set(custIndex, newCust);
    }

    public static Customer getLastAddedCust() throws SQLException {
        String sql = "SELECT * FROM Customers ORDER BY Customer_ID DESC LIMIT 1";
        PreparedStatement ps = DBConnection.getConnection().prepareStatement(sql);
        ResultSet rs = ps.executeQuery();
        if (rs.next())
            return createCustomerObj(rs);
        else
            return null;
    }
}
