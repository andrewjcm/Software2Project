package dao;

import model.Country;
import javafx.collections.ObservableList;
import javafx.collections.FXCollections;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;


public class CountriesDao {

    private static Country createCountryObj(ResultSet resultSet) throws SQLException {
        int id = resultSet.getInt("Country_ID");
        String name = resultSet.getString("Country");
        Timestamp createDate = resultSet.getTimestamp("Create_Date");
        String createdBy = resultSet.getString("Created_By");
        Timestamp updateDate = resultSet.getTimestamp("Last_Update");
        String updatedBy = resultSet.getString("Last_Updated_By");

        return new Country(id, name, createDate, createdBy, updateDate, updatedBy);
    }

    public static ObservableList<Country> getAllCountries () {
        ObservableList<Country> allCountries = FXCollections.observableArrayList();

        try {
            String sql = "SELECT * FROM Countries";

            PreparedStatement ps = DBConnection.getConnection().prepareStatement(sql);

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                allCountries.add(createCountryObj(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return allCountries;
    }

    public static Country getCountry(int id) throws SQLException {
            String sql = "SELECT * FROM Countries WHERE Country_ID=" + id;
            PreparedStatement ps = DBConnection.getConnection().prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            if (rs.next())
                return createCountryObj(rs);
            else
                return null;
    }
}
