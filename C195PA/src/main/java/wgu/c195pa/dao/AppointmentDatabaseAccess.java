package wgu.c195pa.dao;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import wgu.c195pa.helper.JDBC;
import wgu.c195pa.model.Appointments;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;

public class AppointmentDatabaseAccess {
    public static ObservableList<Appointments> getAppointments() throws SQLException {
        ObservableList<Appointments> appointmentsList = FXCollections.observableArrayList();
        String sql = "Select * from appointments";
        PreparedStatement ps = JDBC.getConnection().prepareStatement(sql);
        ResultSet rs = ps.executeQuery();
        while (rs.next()) {
            int appointmentID = rs.getInt("Appointment_ID");
            String title = rs.getString("Title");
            String description = rs.getString("Description");
            String location = rs.getString("Location");
            String type = rs.getString("Type");
            Timestamp start = rs.getTimestamp("Start");
            Timestamp end = rs.getTimestamp("End");
            Timestamp createdDate = rs.getTimestamp("Create_Date");
            String createdBy = rs.getString("Created_By");
            Timestamp lastUpdated = rs.getTimestamp("Last_Update");
            String lastUpdatedBy = rs.getString("Last_Updated_By");
            int customerID = rs.getInt("Customer_ID");
            int userID = rs.getInt("User_ID");
            int contactID = rs.getInt("Contact_ID");

            Appointments appointment = new Appointments(appointmentID, title, description, location, type, start, end, createdDate, createdBy, lastUpdated, lastUpdatedBy, customerID, userID, contactID);
            appointmentsList.add(appointment);
        }
        return appointmentsList;
    }
    public static ObservableList<Appointments> getAppointmentsFromWeek() throws SQLException {
        ObservableList<Appointments> appointmentsList = FXCollections.observableArrayList();
        String sql = "SELECT * FROM appointments WHERE Start BETWEEN NOW() AND NOW() + INTERVAL 1 WEEK";
        PreparedStatement ps = JDBC.getConnection().prepareStatement(sql);
        ResultSet rs = ps.executeQuery();
        while (rs.next()) {
            int appointmentID = rs.getInt("Appointment_ID");
            String title = rs.getString("Title");
            String description = rs.getString("Description");
            String location = rs.getString("Location");
            String type = rs.getString("Type");
            Timestamp start = rs.getTimestamp("Start");
            Timestamp end = rs.getTimestamp("End");
            Timestamp createdDate = rs.getTimestamp("Create_Date");
            String createdBy = rs.getString("Created_By");
            Timestamp lastUpdated = rs.getTimestamp("Last_Update");
            String lastUpdatedBy = rs.getString("Last_Updated_By");
            int customerID = rs.getInt("Customer_ID");
            int userID = rs.getInt("User_ID");
            int contactID = rs.getInt("Contact_ID");

            Appointments appointment = new Appointments(appointmentID, title, description, location, type, start, end, createdDate, createdBy, lastUpdated, lastUpdatedBy, customerID, userID, contactID);
            appointmentsList.add(appointment);
        }
        return appointmentsList;
    }
    public static ObservableList<Appointments> getAppointmentsFromMonth() throws SQLException {
        ObservableList<Appointments> appointmentsList = FXCollections.observableArrayList();
        String sql = "SELECT * FROM appointments WHERE Start BETWEEN NOW() AND NOW() + INTERVAL 1 MONTH";
        PreparedStatement ps = JDBC.getConnection().prepareStatement(sql);
        ResultSet rs = ps.executeQuery();
        while (rs.next()) {
            int appointmentID = rs.getInt("Appointment_ID");
            String title = rs.getString("Title");
            String description = rs.getString("Description");
            String location = rs.getString("Location");
            String type = rs.getString("Type");
            Timestamp start = rs.getTimestamp("Start");
            Timestamp end = rs.getTimestamp("End");
            Timestamp createdDate = rs.getTimestamp("Create_Date");
            String createdBy = rs.getString("Created_By");
            Timestamp lastUpdated = rs.getTimestamp("Last_Update");
            String lastUpdatedBy = rs.getString("Last_Updated_By");
            int customerID = rs.getInt("Customer_ID");
            int userID = rs.getInt("User_ID");
            int contactID = rs.getInt("Contact_ID");

            Appointments appointment = new Appointments(appointmentID, title, description, location, type, start, end, createdDate, createdBy, lastUpdated, lastUpdatedBy, customerID, userID, contactID);
            appointmentsList.add(appointment);
        }
        return appointmentsList;
    }
    public static ObservableList<Appointments> getAppointmentsFrom15Mins() throws SQLException {
        ObservableList<Appointments> appointmentsList = FXCollections.observableArrayList();
        String sql = "SELECT * FROM appointments WHERE Start BETWEEN NOW() AND NOW() + INTERVAL 15 MINUTE";
        PreparedStatement ps = JDBC.getConnection().prepareStatement(sql);
        ResultSet rs = ps.executeQuery();
        while (rs.next()) {
            int appointmentID = rs.getInt("Appointment_ID");
            String title = rs.getString("Title");
            String description = rs.getString("Description");
            String location = rs.getString("Location");
            String type = rs.getString("Type");
            Timestamp start = rs.getTimestamp("Start");
            Timestamp end = rs.getTimestamp("End");
            Timestamp createdDate = rs.getTimestamp("Create_Date");
            String createdBy = rs.getString("Created_By");
            Timestamp lastUpdated = rs.getTimestamp("Last_Update");
            String lastUpdatedBy = rs.getString("Last_Updated_By");
            int customerID = rs.getInt("Customer_ID");
            int userID = rs.getInt("User_ID");
            int contactID = rs.getInt("Contact_ID");


            Appointments appointment = new Appointments(appointmentID, title, description, location, type, start, end, createdDate, createdBy, lastUpdated, lastUpdatedBy, customerID, userID, contactID);
            appointmentsList.add(appointment);
        }
        return appointmentsList;
    }

}