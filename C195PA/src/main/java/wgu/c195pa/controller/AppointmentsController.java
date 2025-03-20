package wgu.c195pa.controller;

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
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Objects;
import java.util.Optional;
import java.util.ResourceBundle;


public class AppointmentsController implements Initializable {
    Stage stage;
    Parent scene;
    private static Appointments selectedAppointment = null;

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
    private TableColumn<Appointments, LocalDateTime> startDateColumn;

    @FXML
    private TableColumn<Appointments, LocalDate> endDateColumn;

    @FXML
    private TableColumn<Appointments, Integer> customerIDColumn;

    public void backButton(ActionEvent actionEvent) throws IOException {
        stage = (Stage) ((Button) actionEvent.getSource()).getScene().getWindow();
        scene = FXMLLoader.load((Objects.requireNonNull(getClass().getResource("/wgu/c195pa/home.fxml"))));
        stage.setScene(new Scene(scene));
        stage.show();
    }
    public void addAppointment(ActionEvent actionEvent) throws IOException {
        selectedAppointment = null;
        stage = (Stage) ((Button) actionEvent.getSource()).getScene().getWindow();
        scene = FXMLLoader.load((Objects.requireNonNull(getClass().getResource("/wgu/c195pa/appointmentEditor.fxml"))));
        stage.setScene(new Scene(scene));
        stage.show();
    }

    public void updateAppointment(ActionEvent actionEvent) throws IOException {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Appointments");
        alert.setContentText("No appointment was selected");
        selectedAppointment = appointmentTable.getSelectionModel().getSelectedItem();
        if(selectedAppointment == null){
            alert.showAndWait();
        }else{
        stage = (Stage) ((Button) actionEvent.getSource()).getScene().getWindow();
        scene = FXMLLoader.load((Objects.requireNonNull(getClass().getResource("/wgu/c195pa/appointmentEditor.fxml"))));
        stage.setScene(new Scene(scene));
        stage.show();}
    }
    public static Appointments getSelectedAppointment(){
        return selectedAppointment;
    }

    public void deleteAppointment(ActionEvent actionEvent) throws SQLException {
        selectedAppointment = appointmentTable.getSelectionModel().getSelectedItem();

        if(selectedAppointment != null){
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setContentText("Are you sure you want to delete this appointment?");
            Optional<ButtonType> result = alert.showAndWait();
            if(result.isPresent() && result.get() == ButtonType.OK){
                Connection connection = JDBC.getConnection();
                String deleteStatement = "DELETE FROM appointments WHERE Appointment_ID = ?";
                PreparedStatement statement = connection.prepareStatement(deleteStatement);
                statement.setInt(1, selectedAppointment.getAppointmentID());
                statement.executeUpdate();

                appointmentTable.setItems(AppointmentDatabaseAccess.getAppointments());

                appointmentIDColumn.setCellValueFactory(new PropertyValueFactory<>("appointmentID"));
                titleColumn.setCellValueFactory(new PropertyValueFactory<>("title"));
                descriptionColumn.setCellValueFactory(new PropertyValueFactory<>("description"));
                locationColumn.setCellValueFactory(new PropertyValueFactory<>("location"));
                contactColumn.setCellValueFactory(new PropertyValueFactory<>("contactID"));
                typeColumn.setCellValueFactory(new PropertyValueFactory<>("type"));
                startDateColumn.setCellValueFactory(new PropertyValueFactory<>("start"));
                endDateColumn.setCellValueFactory((new PropertyValueFactory<>("end")));
                customerIDColumn.setCellValueFactory(new PropertyValueFactory<>("customerID"));
            }
        }
        else {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setContentText("No appointment selected");
            alert.showAndWait();

        }
    }
    public void selectWeek(ActionEvent actionEvent) throws SQLException {
        appointmentTable.setItems(AppointmentDatabaseAccess.getAppointmentsFromWeek());

        appointmentIDColumn.setCellValueFactory(new PropertyValueFactory<>("appointmentID"));
        titleColumn.setCellValueFactory(new PropertyValueFactory<>("title"));
        descriptionColumn.setCellValueFactory(new PropertyValueFactory<>("description"));
        locationColumn.setCellValueFactory(new PropertyValueFactory<>("location"));
        contactColumn.setCellValueFactory(new PropertyValueFactory<>("contactID"));
        typeColumn.setCellValueFactory(new PropertyValueFactory<>("type"));
        startDateColumn.setCellValueFactory(new PropertyValueFactory<>("start"));
        endDateColumn.setCellValueFactory((new PropertyValueFactory<>("end")));
        customerIDColumn.setCellValueFactory(new PropertyValueFactory<>("customerID"));
    }
    public void selectMonth(ActionEvent actionEvent) throws SQLException {
        appointmentTable.setItems(AppointmentDatabaseAccess.getAppointmentsFromMonth());

        appointmentIDColumn.setCellValueFactory(new PropertyValueFactory<>("appointmentID"));
        titleColumn.setCellValueFactory(new PropertyValueFactory<>("title"));
        descriptionColumn.setCellValueFactory(new PropertyValueFactory<>("description"));
        locationColumn.setCellValueFactory(new PropertyValueFactory<>("location"));
        contactColumn.setCellValueFactory(new PropertyValueFactory<>("contactID"));
        typeColumn.setCellValueFactory(new PropertyValueFactory<>("type"));
        startDateColumn.setCellValueFactory(new PropertyValueFactory<>("start"));
        endDateColumn.setCellValueFactory((new PropertyValueFactory<>("end")));
        customerIDColumn.setCellValueFactory(new PropertyValueFactory<>("customerID"));
    }
    public void selectAll(ActionEvent actionEvent) throws SQLException {
        appointmentTable.setItems(AppointmentDatabaseAccess.getAppointments());

        appointmentIDColumn.setCellValueFactory(new PropertyValueFactory<>("appointmentID"));
        titleColumn.setCellValueFactory(new PropertyValueFactory<>("title"));
        descriptionColumn.setCellValueFactory(new PropertyValueFactory<>("description"));
        locationColumn.setCellValueFactory(new PropertyValueFactory<>("location"));
        contactColumn.setCellValueFactory(new PropertyValueFactory<>("contactID"));
        typeColumn.setCellValueFactory(new PropertyValueFactory<>("type"));
        startDateColumn.setCellValueFactory(new PropertyValueFactory<>("start"));
        endDateColumn.setCellValueFactory((new PropertyValueFactory<>("end")));
        customerIDColumn.setCellValueFactory(new PropertyValueFactory<>("customerID"));
    }


    @Override
    public void initialize (URL url, ResourceBundle resourceBundle) {

        try{
            appointmentTable.setItems(AppointmentDatabaseAccess.getAppointments());

            appointmentIDColumn.setCellValueFactory(new PropertyValueFactory<>("appointmentID"));
            titleColumn.setCellValueFactory(new PropertyValueFactory<>("title"));
            descriptionColumn.setCellValueFactory(new PropertyValueFactory<>("description"));
            locationColumn.setCellValueFactory(new PropertyValueFactory<>("location"));
            contactColumn.setCellValueFactory(new PropertyValueFactory<>("contactID"));
            typeColumn.setCellValueFactory(new PropertyValueFactory<>("type"));
            startDateColumn.setCellValueFactory(new PropertyValueFactory<>("start"));
            endDateColumn.setCellValueFactory((new PropertyValueFactory<>("end")));
            customerIDColumn.setCellValueFactory(new PropertyValueFactory<>("customerID"));
        }
        catch (SQLException sql){
            System.out.println(sql.getMessage());
            sql.printStackTrace();
        }
    }




}
