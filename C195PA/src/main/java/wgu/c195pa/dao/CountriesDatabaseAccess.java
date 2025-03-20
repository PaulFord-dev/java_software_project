package wgu.c195pa.dao;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import wgu.c195pa.helper.JDBC;
import wgu.c195pa.model.Countries;

import java.sql.*;

public class CountriesDatabaseAccess {
    public static ObservableList<Countries> getCountries() throws SQLException {
        ObservableList<Countries> countriesList = FXCollections.observableArrayList();
        String sql = "Select * from countries";
        PreparedStatement ps = JDBC.getConnection().prepareStatement(sql);
        ResultSet rs = ps.executeQuery();
        while(rs.next()){
            int countryId = rs.getInt("Country_ID");
            String countryName = rs.getString("Country");
            Date createDate = rs.getDate("Create_Date");
            String createdBy = rs.getString("Created_By");
            Timestamp lastUpdate = rs.getTimestamp("Last_Update");
            String lastUpdatedBy = rs.getString("Last_Updated_By");

            Countries country = new Countries(countryId, countryName, createDate, createdBy, lastUpdate, lastUpdatedBy);
            countriesList.add(country);
        }
        return countriesList;
    }
    public static ObservableList<String> getCountriesNameList() throws SQLException{
        ObservableList<String> countriesNameList = FXCollections.observableArrayList();
        String query = "SELECT Country FROM countries";
        Statement statement = JDBC.getConnection().createStatement();
        ResultSet result = statement.executeQuery(query);

        while (result.next()) {
            String countryName = result.getString("Country");
            countriesNameList.add(countryName);
        }
        return countriesNameList;
    }

}
