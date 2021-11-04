package dao;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Customer;
import model.Division;
import utils.time.ZoneLocalize;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;

/**
 * Customer data access object.
 * @author Andrew Cesar-Metzgus
 */
public class CustomersDao {

    private static ObservableList<Customer> allCustomers = FXCollections.observableArrayList();
    private static int custId = -1;

    /**
     * Gets next id.
     * @return int Next ID.
     */
    public static int getIncrementedCustId() {
        if (custId == -1){
            int lastApptIndex = allCustomers.size()-1;
            custId = allCustomers.get(lastApptIndex).getId();
        }

        return ++custId;
    }

    /**
     * Creates an Customer object from a database results set.
     * <p><br>
     * <b>Lambda Use Justification</b>
     *  In order to create a Customer Java class object within the program, it
     *  it must contain a Division object. This is part of the relational database.
     *  The lambda function queries the Division DOA to find the related object
     *  more effectively using a stream and filter, rather than a for loop.
     *  </p>
     * @param rs Results Set
     * @return Customer object.
     * @throws SQLException
     */
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

    /**
     * Gets all customers from the database if does not already exist in program memory.
     * @return ObservableList of Customers.
     */
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

    /**
     * Adds customer to database and program memory,
     * @param appt Customer object.
     * @throws SQLException
     */
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

    /**
     * Deletes customer from database and program memory.
     * @param appt Customer object.
     * @throws SQLException
     */
    public static void deleteCustomer(Customer customer) throws SQLException {

        String sql = "DELETE FROM Appointments WHERE Customer_ID=" + customer.getId();

        PreparedStatement ps = DBConnection.getConnection().prepareStatement(sql);

        ps.execute();

        // Locate in RAM list and remove

        if (allCustomers.size() > 0)
            allCustomers.remove(customer);
    }

    /**
     * Updates customer from database and program memory.
     * @param apptIndex int customer index in program memory list.
     * @param origAppt Original customer object.
     * @param newAppt New customer object.
     * @throws SQLException
     */
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

    /**
     * Get's the last added customer from the database.
     * @return Customer object or null.
     * @throws SQLException
     */
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
