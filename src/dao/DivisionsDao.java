package dao;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Country;
import model.Division;
import utils.Time.TZConverter;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;

public class DivisionsDao {

    private static ObservableList<Division> allDivisions = FXCollections.observableArrayList();

    private static Division createDivisionObj(ResultSet rs) throws SQLException {
        int id = rs.getInt("Division_ID");
        String name = rs.getString("Division");
        Timestamp createDate = rs.getTimestamp("Create_Date");
        String createdBy = rs.getString("Created_By");
        Timestamp updateDate = rs.getTimestamp("Last_Update");
        String updatedBy = rs.getString("Last_Updated_By");
        int country_id = rs.getInt("Country_ID");

        // Lambda function to locate the Country object in an ObservableList by ID.
        Country country = CountriesDao.getAllCountries().stream().filter(
                c -> c.getId() == country_id
        ).findFirst().orElse(null);
        return new Division(
                id, name, TZConverter.fromDb(createDate), createdBy,
                TZConverter.fromDb(updateDate), updatedBy, country
        );
    }

    public static ObservableList<Division> getAllDivisions(){
        if (allDivisions.size() == 0) {
            try {
                String sql = "SELECT * FROM first_level_divisions";

                PreparedStatement ps = DBConnection.getConnection().prepareStatement(sql);

                ResultSet rs = ps.executeQuery();

                while (rs.next()) {
                    allDivisions.add(createDivisionObj(rs));
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return allDivisions;
    }

    public static Division getDivision(int id) throws SQLException {
        String sql = "SELECT * FROM first_level_divisions WHERE Division_ID=" + id;
        PreparedStatement ps = DBConnection.getConnection().prepareStatement(sql);
        ResultSet rs = ps.executeQuery();
        if (rs.next())
            return createDivisionObj(rs);
        else
            return null;
    }
}

