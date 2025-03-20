package wgu.c195pa.dao;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import wgu.c195pa.helper.JDBC;
import wgu.c195pa.model.Contacts;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class ContactsDatabaseAccess {
    public static ObservableList<Contacts> getContacts() throws SQLException {
        ObservableList<Contacts> contactsList = FXCollections.observableArrayList();
        String sql = "Select * from contacts";
        PreparedStatement ps = JDBC.getConnection().prepareStatement(sql);
        ResultSet rs = ps.executeQuery();
        while (rs.next()) {
            int contactId = rs.getInt("Contact_ID");
            String contactName = rs.getString("Contact_Name");
            String email = rs.getString("Email");
            Contacts contact = new Contacts(contactId, contactName, email);
            contactsList.add(contact);
        }
        return contactsList;
    }
    public static ObservableList<String> getContactNameList() throws SQLException {
        ObservableList<String> contactNameList = FXCollections.observableArrayList();
        String query = "SELECT Contact_Name FROM contacts";
        Statement statement = JDBC.getConnection().createStatement();
        ResultSet result = statement.executeQuery(query);

        while (result.next()) {
            String contactName = result.getString("Contact_Name");
            contactNameList.add(contactName);
        }
        return contactNameList;
    }

}
