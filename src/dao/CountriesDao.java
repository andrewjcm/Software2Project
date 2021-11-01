package dao;

import model.Country;
import javafx.collections.ObservableList;
import javafx.collections.FXCollections;
import utils.time.ZoneLocalize;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;

/**
 * Country data access object.
 * @author Andrew Cesar-Metzgus
 */
public class CountriesDao {
    private static ObservableList<Country> allCountries = FXCollections.observableArrayList();

    /**
     * Creates a Country object from a database results set.
     * @param rs Results Set
     * @return Country object.
     * @throws SQLException
     */
    private static Country createCountryObj(ResultSet resultSet) throws SQLException {
        int id = resultSet.getInt("Country_ID");
        String name = resultSet.getString("Country");
        Timestamp createDate = resultSet.getTimestamp("Create_Date");
        String createdBy = resultSet.getString("Created_By");
        Timestamp updateDate = resultSet.getTimestamp("Last_Update");
        String updatedBy = resultSet.getString("Last_Updated_By");

        return new Country(id, name, ZoneLocalize.toSysDefault(createDate), createdBy, ZoneLocalize.toSysDefault(updateDate), updatedBy);
    }

    /**
     * Gets all countries from database if not already in program memory.
     * @return ObservableList of countries.
     */
    public static ObservableList<Country> getAllCountries () {
        if (allCountries.isEmpty()) {
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
        }

        return allCountries;
    }
}
