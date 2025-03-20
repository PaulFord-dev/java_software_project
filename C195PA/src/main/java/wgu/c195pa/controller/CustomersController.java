package wgu.c195pa.controller;

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
import wgu.c195pa.dao.CustomerDatabaseAccess;
import wgu.c195pa.helper.JDBC;
import wgu.c195pa.model.Appointments;
import wgu.c195pa.model.Customers;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Objects;
import java.util.Optional;
import java.util.ResourceBundle;

public class CustomersController implements Initializable {
    public Label selectedLabel;
    Stage stage;
    Parent scene;
    static Customers selectedCustomer = null;

    @FXML
    private TableView<Customers> customerTable;
    @FXML
    private TableColumn<Customers, Integer> customerID;
    @FXML
    private TableColumn<Customers, String> customerName;
    @FXML
    private TableColumn<Customers, String> customerAddress;
    @FXML
    private TableColumn<Customers, String> customerPostalCode;
    @FXML
    private TableColumn<Customers, String> customerPhone;
    @FXML
    private TableColumn<Customers, String> customerState;
    @FXML
    private TableColumn<Customers, String> customerCountry;


    public void backButton(ActionEvent actionEvent) throws IOException {
        stage = (Stage) ((Button)actionEvent.getSource()).getScene().getWindow();
        scene = FXMLLoader.load((Objects.requireNonNull(getClass().getResource("/wgu/c195pa/home.fxml"))));
        stage.setScene(new Scene(scene));
        stage.show();
    }

    public void addCustomer(ActionEvent actionEvent) throws IOException {
        selectedCustomer = null;
        stage = (Stage) ((Button) actionEvent.getSource()).getScene().getWindow();
        scene = FXMLLoader.load((Objects.requireNonNull(getClass().getResource("/wgu/c195pa/customerEditor.fxml"))));
        stage.setScene(new Scene(scene));
        stage.show();
    }

    public void updateCustomer(ActionEvent actionEvent) throws IOException {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Customers");
        alert.setContentText("No customer was selected");
        selectedCustomer = customerTable.getSelectionModel().getSelectedItem();
        if(selectedCustomer ==null){
            alert.showAndWait();
        }else{
        stage = (Stage) ((Button) actionEvent.getSource()).getScene().getWindow();
        scene = FXMLLoader.load((Objects.requireNonNull(getClass().getResource("/wgu/c195pa/customerEditor.fxml"))));
        stage.setScene(new Scene(scene));
        stage.show();}
    }

    public static Customers getSelectedCustomer() {
        return selectedCustomer;
    }

    public void deleteCustomer(ActionEvent actionEvent) throws SQLException {
        Customers selectedCustomer = customerTable.getSelectionModel().getSelectedItem();

        if (selectedCustomer != null) {
            int selectedCustomerID = selectedCustomer.getCustomerID();
            ObservableList<Appointments> appointmentList = AppointmentDatabaseAccess.getAppointments();
            for (Appointments appointments : appointmentList) {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                if (appointments.getCustomerID() == selectedCustomer.getCustomerID()) {
                    alert.setContentText("Customer cannot be deleted because of a pending appointment");
                    alert.showAndWait();
                    return;
                }else {
                    alert.setContentText("Are you sure you want to delete this customer?");
                    Optional<ButtonType> result = alert.showAndWait();
                    if(result.isPresent() && result.get() == ButtonType.OK){
                        Connection connection = JDBC.getConnection();
                        String deleteStatement = "DELETE FROM customers WHERE Customer_ID = " + selectedCustomerID;
                        PreparedStatement statement = connection.prepareStatement(deleteStatement);
                        statement.executeUpdate();

                        customerTable.setItems(CustomerDatabaseAccess.getCustomers());
                        customerID.setCellValueFactory(new PropertyValueFactory<>("customerID"));
                        customerName.setCellValueFactory(new PropertyValueFactory<>("customerName"));
                        customerAddress.setCellValueFactory(new PropertyValueFactory<>("customerAddress"));
                        customerPostalCode.setCellValueFactory(new PropertyValueFactory<>("postalCode"));
                        customerPhone.setCellValueFactory(new PropertyValueFactory<>("phoneNumber"));
                    }
                break;
                }
            }
        }else{
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setContentText("No customer selected");
            alert.showAndWait();

        }
    }

    public void initialize (URL url, ResourceBundle resourceBundle) {
        try {
            customerTable.setItems(CustomerDatabaseAccess.getCustomers());

            customerID.setCellValueFactory(new PropertyValueFactory<>("customerID"));
            customerName.setCellValueFactory(new PropertyValueFactory<>("customerName"));
            customerAddress.setCellValueFactory(new PropertyValueFactory<>("customerAddress"));
            customerPostalCode.setCellValueFactory(new PropertyValueFactory<>("postalCode"));
            customerPhone.setCellValueFactory(new PropertyValueFactory<>("phoneNumber"));
            customerState.setCellValueFactory(new PropertyValueFactory<>("division"));
            customerCountry.setCellValueFactory(new PropertyValueFactory<>("country"));
        } catch (SQLException sql) {
            System.out.println(sql.getMessage());
            sql.printStackTrace();
        }

        customerTable.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                selectedLabel.setText("Selected Customer: " + String.valueOf(newSelection.getCustomerName()));
            }
        });
    }

}

