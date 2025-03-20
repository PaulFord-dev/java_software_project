package wgu.c195pa.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import wgu.c195pa.dao.AppointmentDatabaseAccess;
import wgu.c195pa.dao.ContactsDatabaseAccess;
import wgu.c195pa.model.Appointments;
import wgu.c195pa.model.Contacts;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.Objects;
import java.util.ResourceBundle;
import java.util.concurrent.atomic.AtomicInteger;

public class ApptContactReportController implements Initializable {
    public ComboBox<String> contactsBox;
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
    private TableColumn<Appointments, LocalDate> startDateColumn;

    @FXML
    private TableColumn<Appointments, LocalDate> endDateColumn;

    @FXML
    private TableColumn<Appointments, Integer> customerIDColumn;
    public void selectContact(ActionEvent actionEvent) throws SQLException {
        String selectedContactName = contactsBox.getValue();
        AtomicInteger selectedContactID = new AtomicInteger();
        ObservableList<Contacts> contactsList = FXCollections.observableArrayList();
        contactsList = ContactsDatabaseAccess.getContacts();

//        for (Contacts contact : contactsList){
//            if (selectedContactName.equals(contact.getContactName())){
//                selectedContactID.set(contact.getContactID());
//                break;
//            }
//        }

        contactsList.forEach(c -> {
            if (selectedContactName.equals(c.getContactName())) {
                selectedContactID.set(c.getContactID());
            }
        });

        ObservableList<Appointments> appointmentsList= FXCollections.observableArrayList();
        appointmentsList = AppointmentDatabaseAccess.getAppointments();

        ObservableList<Appointments> appointmentsListFromContactID= FXCollections.observableArrayList();

        for (Appointments appointment : appointmentsList){
            if (appointment.getContactID() == selectedContactID.get()){
                appointmentsListFromContactID.add(appointment);
            }
        }

        appointmentTable.setItems(appointmentsListFromContactID);

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
    public void backButton(ActionEvent actionEvent) throws IOException {
        stage = (Stage) ((Button) actionEvent.getSource()).getScene().getWindow();
        scene = FXMLLoader.load((Objects.requireNonNull(getClass().getResource("/wgu/c195pa/reports.fxml"))));
        stage.setScene(new Scene(scene));
        stage.show();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            contactsBox.setItems(ContactsDatabaseAccess.getContactNameList());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
