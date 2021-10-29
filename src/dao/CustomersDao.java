package dao;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Customer;
import model.Division;
import utils.Time.TZConverter;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;

public class CustomersDao {

    private static ObservableList<Customer> allCustomers = FXCollections.observableArrayList();

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
                    id, name, address, zip, phone, TZConverter.fromDb(createDate), createdBy,
                    TZConverter.fromDb(updateDate), updatedBy, division
            );
    }

    public static ObservableList<Customer> getAllCustomers() {
        if (allCustomers.size() == 0) {
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
                + "Customer_ID, Customer_Name, Address, Postal_Code, Phone, Create_Date,"
                + " Created_By, Last_Update, Last_Updated_By, Division_ID)"
                + " values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        PreparedStatement ps = DBConnection.getConnection().prepareStatement(sql);

        ps.setInt(1, customer.getId());
        ps.setString(2, customer.getName());
        ps.setString(3, customer.getAddress());
        ps.setString(4, customer.getPostalCode());
        ps.setString(5, customer.getPhone());
        ps.setTimestamp(6, TZConverter.toDb(customer.getCreateDate()));
        ps.setString(7, customer.getCreatedBy());
        ps.setTimestamp(8, TZConverter.toDb(customer.getLastUpdate()));
        ps.setString(9, customer.getLastUpdatedBy());
        ps.setInt(10, customer.getDivision().getId());

        ps.execute();

        // add to ram list
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
}
