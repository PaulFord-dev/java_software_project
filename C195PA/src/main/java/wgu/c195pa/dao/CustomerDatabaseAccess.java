package wgu.c195pa.dao;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import wgu.c195pa.helper.JDBC;
import wgu.c195pa.model.Customers;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class CustomerDatabaseAccess {
    public static ObservableList<Customers> getCustomers() throws SQLException {

        ObservableList<Customers> customerList = FXCollections.observableArrayList();
        String sql = "SELECT Customer_ID, Customer_Name, Address, Postal_Code, Phone, customers.Created_by, customers.Last_Updated_By, customers.Division_ID, Division, country FROM customers\n" +
                "JOIN first_level_divisions on customers.Division_ID = first_level_divisions.Division_ID\n" +
                "JOIN countries on first_level_divisions.Country_ID = countries.Country_ID;";
        PreparedStatement ps = JDBC.getConnection().prepareStatement(sql);
        ResultSet rs = ps.executeQuery();
        while (rs.next()) {
            int customerID = rs.getInt("Customer_ID");
            String customerName = rs.getString("Customer_Name");
            String customerAddress = rs.getString("Address");
            String postalCode = rs.getString("Postal_Code");
            String phoneNumber = rs.getString("Phone");
            String createdBy = rs.getString("Created_By");
            String lastUpdateBy = rs.getString("Last_Updated_By");
            int customerDivisionID = rs.getInt("Division_ID");
            String division = rs.getString("Division");
            String country = rs.getString("country");

            Customers Customer = new Customers(customerID, customerName, customerAddress, postalCode, phoneNumber, createdBy, lastUpdateBy, customerDivisionID, division, country);
            customerList.add(Customer);
        }
        return customerList;
    }
    public static ObservableList<Integer> getCustomerIDList() throws SQLException {
        ObservableList<Integer> customerIDList = FXCollections.observableArrayList();
        String query = "SELECT Customer_ID FROM customers";
        Statement statement = JDBC.getConnection().createStatement();
        ResultSet result = statement.executeQuery(query);

        while (result.next()) {
            Integer userID = result.getInt("Customer_ID");
            customerIDList.add(userID);
        }
        return customerIDList;
    }

}
