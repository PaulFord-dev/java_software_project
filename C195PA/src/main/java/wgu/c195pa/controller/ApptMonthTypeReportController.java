package wgu.c195pa.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import wgu.c195pa.dao.AppointmentDatabaseAccess;
import wgu.c195pa.helper.JDBC;
import wgu.c195pa.model.Appointments;

import java.io.IOException;
import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Objects;
import java.util.ResourceBundle;

public class ApptMonthTypeReportController implements Initializable {
    public Label returnedReportsNumber;
    Stage stage;
    Parent scene;
    public ComboBox<String> typeBox;
    public ComboBox<String> monthBox;

    @FXML
    private TableView<Appointments> appointmentTable;

    @FXML
    private TableColumn<Appointments, Integer> appointmentIDColumn;

    @FXML
    private TableColumn<Appointments, String> titleColumn;

    @FXML
    private TableColumn<Appointments, String> descriptionColumn;

    @FXML
    private TableColumn<Appointments, String> locationColumn;

    @FXML
    private TableColumn<Appointments, String> contactColumn;

    @FXML
    private TableColumn<Appointments, String> typeColumn;

    @FXML
    private TableColumn<Appointments, LocalDate> startDateColumn;

    @FXML
    private TableColumn<Appointments, LocalDate> endDateColumn;

    @FXML
    private TableColumn<Appointments, Integer> customerIDColumn;

    public void showAppointments(ActionEvent actionEvent) throws SQLException {
        HashMap<String,Integer> monthsNumbers = new HashMap<>();
        monthsNumbers.put("January", 1);
        monthsNumbers.put("February", 2);
        monthsNumbers.put("March", 3);
        monthsNumbers.put("April", 4);
        monthsNumbers.put("May", 5);
        monthsNumbers.put("June", 6);
        monthsNumbers.put("July", 7);
        monthsNumbers.put("August", 8);
        monthsNumbers.put("September", 9);
        monthsNumbers.put("October", 10);
        monthsNumbers.put("November", 11);
        monthsNumbers.put("December", 12);

        String selectedMonth = monthBox.getValue();
        Integer monthNumber = monthsNumbers.get(selectedMonth);
        String typeFromBox = typeBox.getValue();

        if (typeFromBox == null || monthNumber == null){
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setContentText("A month and type must be selected");
            alert.showAndWait();
        }

        ObservableList<Appointments> appointmentsList = FXCollections.observableArrayList();
        String sql = "SELECT * FROM appointments WHERE MONTH(Start) = " + monthNumber + " AND Type = '" + typeFromBox + "'";
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
        appointmentTable.setItems(appointmentsList);

        appointmentIDColumn.setCellValueFactory(new PropertyValueFactory<>("appointmentID"));
        titleColumn.setCellValueFactory(new PropertyValueFactory<>("title"));
        descriptionColumn.setCellValueFactory(new PropertyValueFactory<>("description"));
        locationColumn.setCellValueFactory(new PropertyValueFactory<>("location"));
        contactColumn.setCellValueFactory(new PropertyValueFactory<>("contactID"));
        typeColumn.setCellValueFactory(new PropertyValueFactory<>("type"));
        startDateColumn.setCellValueFactory(new PropertyValueFactory<>("start"));
        endDateColumn.setCellValueFactory((new PropertyValueFactory<>("end")));
        customerIDColumn.setCellValueFactory(new PropertyValueFactory<>("customerID"));

        returnedReportsNumber.setText("Appointments Returned: " + appointmentsList.size());

    }

    public void backButton(ActionEvent actionEvent) throws IOException {
        stage = (Stage) ((Button) actionEvent.getSource()).getScene().getWindow();
        scene = FXMLLoader.load((Objects.requireNonNull(getClass().getResource("/wgu/c195pa/reports.fxml"))));
        stage.setScene(new Scene(scene));
        stage.show();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        ObservableList<Appointments> appointmentsList = FXCollections.observableArrayList();
        try {
            appointmentsList = AppointmentDatabaseAccess.getAppointments();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        ObservableList<String> appointmentTypeList = FXCollections.observableArrayList();

        for (Appointments appointment : appointmentsList){
            appointmentTypeList.add(appointment.getType());
        }
        typeBox.setItems(appointmentTypeList);

        ObservableList<String> monthList = FXCollections.observableArrayList();
        monthList.addAll("January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December");
        monthBox.setItems(monthList);
    }


}
