package wgu.c195pa.dao;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import wgu.c195pa.helper.JDBC;
import wgu.c195pa.model.FirstLevelDivisions;

import java.sql.*;

public class FirstLevelDivisionsDatabaseAccess {
    public static ObservableList<FirstLevelDivisions> getFirstLevelDivisions() throws SQLException {
        ObservableList<FirstLevelDivisions> firstLevelDivisionsList = FXCollections.observableArrayList();
        String sql = "Select * from first_level_divisions";
        PreparedStatement ps = JDBC.getConnection().prepareStatement(sql);
        ResultSet rs = ps.executeQuery();
        while (rs.next()) {
            String divisionName = rs.getString("Division");
            int divisionId = rs.getInt("Division_ID");
            Date createDate = rs.getDate("Create_Date");
            String createdBy = rs.getString("Created_By");
            Date lastUpdate = rs.getDate("Last_Update");
            String lastUpdatedBy = rs.getString("Last_Updated_By");
            int countryID = rs.getInt("COUNTRY_ID");

            FirstLevelDivisions firstLevelDivision = new FirstLevelDivisions(divisionId, divisionName, createDate, createdBy, lastUpdate, lastUpdatedBy, countryID);
            firstLevelDivisionsList.add(firstLevelDivision);
        }
        return firstLevelDivisionsList;
    }
    public static ObservableList<String> getCanadianStates() throws SQLException{
        ObservableList<String> canadianStatesList = FXCollections.observableArrayList();
        String query = "Select Division from first_level_divisions where Country_ID = 3";
        Statement statement = JDBC.getConnection().createStatement();
        ResultSet result = statement.executeQuery(query);

        while (result.next()) {
            String countryName = result.getString("Division");
            canadianStatesList.add(countryName);
        }
        return canadianStatesList;
    }
    public static ObservableList<String> getUKStates() throws SQLException{
        ObservableList<String> ukStatesList = FXCollections.observableArrayList();
        String query = "Select Division from first_level_divisions where Country_ID = 2";
        Statement statement = JDBC.getConnection().createStatement();
        ResultSet result = statement.executeQuery(query);

        while (result.next()) {
            String countryName = result.getString("Division");
            ukStatesList.add(countryName);
        }
        return ukStatesList;
    }
    public static ObservableList<String> getUSStates() throws SQLException{
        ObservableList<String> usStatesList = FXCollections.observableArrayList();
        String query = "Select Division from first_level_divisions where Country_ID = 1";
        Statement statement = JDBC.getConnection().createStatement();
        ResultSet result = statement.executeQuery(query);

        while (result.next()) {
            String countryName = result.getString("Division");
            usStatesList.add(countryName);
        }
        return usStatesList;
    }

}
