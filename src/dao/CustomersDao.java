package dao;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Customer;
import model.Division;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;

public class CustomersDao {

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

            // TODO: Create "All" lists for each DOA to check prior to making a DB query.
            Division division = DivisionsDao.getDivision(division_id);
            return new Customer(
                    id, name, address, zip, phone, createDate, createdBy,
                    updateDate, updatedBy, division
            );
    }

    public static ObservableList<Customer> getAllCustomers() {
            ObservableList<Customer> allCustomers = FXCollections.observableArrayList();

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
}
