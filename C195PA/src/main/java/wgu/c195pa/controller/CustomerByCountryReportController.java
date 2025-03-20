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
import wgu.c195pa.dao.CountriesDatabaseAccess;
import wgu.c195pa.dao.CustomerDatabaseAccess;
import wgu.c195pa.model.Customers;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.Objects;
import java.util.ResourceBundle;

public class CustomerByCountryReportController implements Initializable {
    Stage stage;
    Parent scene;
    public ComboBox<String> countryBox;
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
        stage = (Stage) ((Button) actionEvent.getSource()).getScene().getWindow();
        scene = FXMLLoader.load((Objects.requireNonNull(getClass().getResource("/wgu/c195pa/reports.fxml"))));
        stage.setScene(new Scene(scene));
        stage.show();
    }
    public void selectCountry(ActionEvent actionEvent) throws SQLException {
        String selectedCountryName = countryBox.getValue();
        ObservableList<Customers> customerListFromSelectedCountry = FXCollections.observableArrayList();

        ObservableList<Customers> customersList = FXCollections.observableArrayList();
        customersList = CustomerDatabaseAccess.getCustomers();

        for (Customers customer : customersList){
            if (customer.getCountry().equals(selectedCountryName)){
                customerListFromSelectedCountry.add(customer);
            }
        }
        customerTable.setItems(customerListFromSelectedCountry);

        customerID.setCellValueFactory(new PropertyValueFactory<>("customerID"));
        customerName.setCellValueFactory(new PropertyValueFactory<>("customerName"));
        customerAddress.setCellValueFactory(new PropertyValueFactory<>("customerAddress"));
        customerPostalCode.setCellValueFactory(new PropertyValueFactory<>("postalCode"));
        customerPhone.setCellValueFactory(new PropertyValueFactory<>("phoneNumber"));
        customerState.setCellValueFactory(new PropertyValueFactory<>("division"));
        customerCountry.setCellValueFactory(new PropertyValueFactory<>("country"));
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            countryBox.setItems(CountriesDatabaseAccess.getCountriesNameList());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
