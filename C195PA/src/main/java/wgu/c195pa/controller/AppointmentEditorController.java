package wgu.c195pa.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import wgu.c195pa.dao.AppointmentDatabaseAccess;
import wgu.c195pa.dao.ContactsDatabaseAccess;
import wgu.c195pa.dao.CustomerDatabaseAccess;
import wgu.c195pa.dao.UserDatabaseAccess;
import wgu.c195pa.helper.JDBC;
import wgu.c195pa.model.Appointments;
import wgu.c195pa.model.Contacts;

import java.io.IOException;
import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.*;
import java.util.Objects;
import java.util.ResourceBundle;

import static java.lang.String.valueOf;
import static wgu.c195pa.controller.AppointmentsController.getSelectedAppointment;

public class AppointmentEditorController implements Initializable {
    public TextField appointmentIdField;
    public TextField titleField;
    public TextField descriptionField;
    public TextField locationField;
    public TextField typeField;
    public ComboBox<String> contactsBox;
    public DatePicker pickStartDate;
    public ComboBox<LocalTime> startTimeBox;
    public ComboBox<Integer> customerBox;
    public ComboBox<Integer> userBox;
    public DatePicker pickEndDate;
    public ComboBox<LocalTime> endTimeBox;
    Stage stage;
    Parent scene;
    ZonedDateTime openTimeNYC = ZonedDateTime.of(LocalDate.now(),LocalTime.of(8,0), ZoneId.of("America/New_York"));
    ZonedDateTime closeTimeNYC = ZonedDateTime.of(LocalDate.now(),LocalTime.of(22,0), ZoneId.of("America/New_York"));

    public void saveButton(ActionEvent actionEvent) throws SQLException, IOException {
        int appointmentID = Integer.parseInt(appointmentIdField.getText());
        String title = titleField.getText();
        String description = descriptionField.getText();
        String location = locationField.getText();
        String type = typeField.getText();
        Integer customerID = customerBox.getValue();
        Integer userID = userBox.getValue();
        LocalTime startTime = startTimeBox.getValue();
        LocalDate startDate = pickStartDate.getValue();
        LocalTime endTime = endTimeBox.getValue();
        String contactName = contactsBox.getValue();

        if (title.isEmpty() || description.isEmpty() || location.isEmpty() || type.isEmpty() || customerID == null || userID == null || startTime == null ||
                startDate == null || endTime == null || contactName.isEmpty()){
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setContentText("Please fill in all fields before saving appointment");
            alert.showAndWait();
        }else{

            int contactID = 0;
            ObservableList<Contacts> contactList = ContactsDatabaseAccess.getContacts();
            for (Contacts contact : contactList){
                if (Objects.equals(contact.getContactName(), contactName)){
                    contactID = contact.getContactID();
                }
            }

            LocalDateTime startLocal = startDate.atTime(startTime);
            LocalDateTime endLocal = startDate.atTime(endTime);
            Timestamp start = Timestamp.valueOf(startLocal);
            Timestamp end = Timestamp.valueOf(endLocal);
            ObservableList<Appointments> appointmentsList = AppointmentDatabaseAccess.getAppointments();

            for(Appointments appointment : appointmentsList) {
                if (customerID == appointment.getCustomerID() &&
                        appointmentID != appointment.getAppointmentID() &&
                        startLocal.isBefore(appointment.getStart().toLocalDateTime()) &&
                        endLocal.isAfter(appointment.getEnd().toLocalDateTime())) {
                    Alert alert = new Alert(Alert.AlertType.WARNING);
                    alert.setContentText("Appointment overlaps with another appointment");
                    alert.showAndWait();
                    return;
                }
                if(customerID == appointment.getCustomerID() &&
                        appointmentID != appointment.getAppointmentID() &&
                        (startLocal.isEqual(appointment.getStart().toLocalDateTime()) || startLocal.isAfter(appointment.getStart().toLocalDateTime())) &&
                        (endLocal.isEqual(appointment.getEnd().toLocalDateTime())) || endLocal.isBefore(appointment.getEnd().toLocalDateTime())){
                    Alert alert = new Alert(Alert.AlertType.WARNING);
                    alert.setContentText("Appointment overlaps with another appointment");
                    alert.showAndWait();
                    return;
                }
            }

            ObservableList<Appointments> appointmentIDList = AppointmentDatabaseAccess.getAppointments();
            boolean foundID = false;
            for (Appointments appointments : appointmentIDList) {
                if (appointmentID == appointments.getAppointmentID()) {
                    foundID = true;
                    break;
                }
            }

            if (Boolean.TRUE.equals(foundID)) {
                String sql = "UPDATE appointments SET" +
                        " Title = '" + title + "'" +
                        ", Description = '" + description + "'" +
                        ", Location = '" + location + "'" +
                        ", Type = '" + type + "'" +
                        ", User_ID = '" + userID + "'" +
                        ", Customer_ID = '" + customerID + "'" +
                        ", Contact_ID = '" + contactID + "'" +
                        ", Start = '" + start + "'" +
                        ", End = '" + end + "'" +
                        " WHERE Appointment_ID = " + appointmentID;
                PreparedStatement ps = JDBC.getConnection().prepareStatement(sql);
                ps.executeUpdate();

                stage = (Stage) ((Button) actionEvent.getSource()).getScene().getWindow();
                scene = FXMLLoader.load((Objects.requireNonNull(getClass().getResource("/wgu/c195pa/appointments.fxml"))));
                stage.setScene(new Scene(scene));
                stage.show();
                return;
            }

            if (Boolean.FALSE.equals(foundID)) {
                String sql = "INSERT INTO appointments VALUES(" + appointmentID + "," +
                        " '" + title + "'," +
                        " '" + description + "'," +
                        " '" + location + "'," +
                        " '" + type + "'," +
                        " '" + start + "'," +
                        " '" + end + "'," +
                        " NOW(), '', NOW(), ''," +
                        " " + customerID + "," +
                        " " + userID + "," +
                        " " + contactID + ")";
                PreparedStatement ps = JDBC.getConnection().prepareStatement(sql);
                ps.execute();

                stage = (Stage) ((Button) actionEvent.getSource()).getScene().getWindow();
                scene = FXMLLoader.load((Objects.requireNonNull(getClass().getResource("/wgu/c195pa/appointments.fxml"))));
                stage.setScene(new Scene(scene));
                stage.show();
            }
        }
    }

    public void cancelButton(ActionEvent actionEvent) throws IOException {
        stage = (Stage) ((Button) actionEvent.getSource()).getScene().getWindow();
        scene = FXMLLoader.load((Objects.requireNonNull(getClass().getResource("/wgu/c195pa/appointments.fxml"))));
        stage.setScene(new Scene(scene));
        stage.show();
    }
    public void pickDate(ActionEvent actionEvent) {

        LocalDate date = pickStartDate.getValue();
        DayOfWeek day = date.getDayOfWeek();
        if (day == DayOfWeek.SATURDAY) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setContentText("Office is closed weekends.  Please choose a weekday");
            alert.showAndWait();
            pickStartDate.setValue(date.plusDays(2));
        }
        if (day == DayOfWeek.SUNDAY) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setContentText("Office is closed weekends.  Please choose a weekday");
            alert.showAndWait();
            pickStartDate.setValue(date.plusDays(1));
        }

        pickEndDate.setValue(pickStartDate.getValue());
    }

    public void checkStartTime(ActionEvent actionEvent) {
        ZonedDateTime selectedZonedDateTime = ZonedDateTime.of(LocalDate.now(),startTimeBox.getValue(),ZoneId.systemDefault());
        selectedZonedDateTime = selectedZonedDateTime.withZoneSameInstant(ZoneId.of("America/New_York"));
        if (selectedZonedDateTime.isBefore(openTimeNYC) || selectedZonedDateTime.isAfter(closeTimeNYC)){
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setContentText("Please choose a time during business hours");
            alert.showAndWait();
        }


        endTimeBox.setItems(startTimeBox.getItems());
    }
    public void checkEndTime(ActionEvent actionEvent) throws SQLException {
        ZonedDateTime selectedZonedDateTime = ZonedDateTime.of(LocalDate.now(),endTimeBox.getValue(),ZoneId.systemDefault());
        selectedZonedDateTime = selectedZonedDateTime.withZoneSameInstant(ZoneId.of("America/New_York"));
        if (selectedZonedDateTime.isBefore(openTimeNYC) || selectedZonedDateTime.isAfter(closeTimeNYC)){
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setContentText("Please choose a time during business hours");
            alert.showAndWait();
        }

        if (endTimeBox.getValue().isBefore(startTimeBox.getValue())){
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setContentText("Appointment end time must be after start time");
            alert.showAndWait();
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        try {
            contactsBox.setItems(ContactsDatabaseAccess.getContactNameList());
            userBox.setItems(UserDatabaseAccess.getUserIDList());
            customerBox.setItems(CustomerDatabaseAccess.getCustomerIDList());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        LocalTime startTime = LocalTime.of(0, 0);
        ObservableList<LocalTime> times = FXCollections.observableArrayList();
        int index = 0;
        while (index < 96) {
            times.add(startTime);
            startTime = startTime.plusMinutes(15);
            index += 1;
        }
        startTimeBox.setItems(times);

        Appointments selectedAppointment = getSelectedAppointment();
        if (selectedAppointment != null) {
            appointmentIdField.setText(valueOf(selectedAppointment.getAppointmentID()));
            titleField.setText(selectedAppointment.getTitle());
            descriptionField.setText(selectedAppointment.getDescription());
            locationField.setText(selectedAppointment.getLocation());
            typeField.setText(selectedAppointment.getType());
            try {
                contactsBox.setValue(Contacts.getContactNameFromID(selectedAppointment.getContactID()));
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
            customerBox.setValue(selectedAppointment.getCustomerID());
            userBox.setValue(selectedAppointment.getUserID());
            startTimeBox.setValue(selectedAppointment.getStart().toLocalDateTime().toLocalTime());
            endTimeBox.setValue(selectedAppointment.getEnd().toLocalDateTime().toLocalTime());
            pickStartDate.setValue(selectedAppointment.getStart().toLocalDateTime().toLocalDate());
            pickEndDate.setValue(selectedAppointment.getEnd().toLocalDateTime().toLocalDate());
        } else {
            try {
                Integer appointmentID = AppointmentDatabaseAccess.getAppointments().size();
                appointmentID += 1;
                appointmentIdField.setText(valueOf(appointmentID));
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
