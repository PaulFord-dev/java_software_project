package wgu.c195pa.controller;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import wgu.c195pa.dao.CountriesDatabaseAccess;
import wgu.c195pa.dao.CustomerDatabaseAccess;
import wgu.c195pa.dao.FirstLevelDivisionsDatabaseAccess;
import wgu.c195pa.helper.JDBC;
import wgu.c195pa.model.Countries;
import wgu.c195pa.model.Customers;
import wgu.c195pa.model.FirstLevelDivisions;

import java.io.IOException;
import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Objects;
import java.util.ResourceBundle;

import static java.lang.String.valueOf;
import static wgu.c195pa.controller.CustomersController.getSelectedCustomer;

public class CustomerEditorController implements Initializable {
    public TextField customerIdField;
    public TextField customerNameField;
    public TextField customerAddressField;
    public TextField postalCodeField;
    public TextField phoneNumberField;
    public ComboBox<String> stateSelectionBox;
    public ComboBox<String> countrySelectionBox;
    Stage stage;
    Parent scene;

    public void SaveButton(ActionEvent actionEvent) throws SQLException, IOException {
        int customerID = Integer.parseInt(customerIdField.getText());
        String customerName = customerNameField.getText();
        String address = customerAddressField.getText();
        String postalCode = postalCodeField.getText();
        String phoneNumber = phoneNumberField.getText();
        String state = valueOf(stateSelectionBox.getValue());
        String currentUser = LoginController.getCurrentUser();

        if (customerName.isEmpty() || address.isEmpty() || postalCode.isEmpty() || phoneNumber.isEmpty() || state.isEmpty()){
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setContentText("Please fill in all fields before saving customer");
            alert.showAndWait();
        }else{
            int divisionID = 0;
            ObservableList<FirstLevelDivisions> firstLevelDivisionsList = FirstLevelDivisionsDatabaseAccess.getFirstLevelDivisions();


            for(FirstLevelDivisions firstLevelDivision : firstLevelDivisionsList){
                if(Objects.equals(state, firstLevelDivision.getDivision())){
                    divisionID = firstLevelDivision.getDivisionID();
                }
            }

            boolean foundID = false;
            ObservableList<Integer> customerIDList = CustomerDatabaseAccess.getCustomerIDList();

            for(Integer customerIDs : customerIDList){
                if (customerID == customerIDs) {
                    foundID = true;
                    break;
                }
            }
            if(Boolean.TRUE.equals(foundID)){
                String sql = "UPDATE customers SET" +
                        " Customer_Name = '" + customerName + "'" +
                        ", Address = '" + address + "'" +
                        ", Postal_Code = '" + postalCode + "'" +
                        ", Phone = '" + phoneNumber + "'" +
                        ", Create_Date = NOW(), Last_Update =  NOW()" +
                        ", Division_ID = '" + divisionID +
                        "' WHERE Customer_ID = " + customerID;
                System.out.println(sql);
                PreparedStatement ps = JDBC.getConnection().prepareStatement(sql);
                ps.executeUpdate();

                stage = (Stage) ((Button) actionEvent.getSource()).getScene().getWindow();
                scene = FXMLLoader.load((Objects.requireNonNull(getClass().getResource("/wgu/c195pa/customers.fxml"))));
                stage.setScene(new Scene(scene));
                stage.show();
            }
            else if (Boolean.FALSE.equals(foundID)){
                String sql = "INSERT INTO customers VALUES(" + customerID + "," +
                        " '" + customerName + "'," +
                        " '" + address + "'," +
                        " '" + postalCode + "'," +
                        " '" + phoneNumber + "'," +
                        " NOW(), '', NOW(), '', " + divisionID + ")";
                System.out.println(sql);
                PreparedStatement ps = JDBC.getConnection().prepareStatement(sql);
                ps.execute();

                stage = (Stage) ((Button) actionEvent.getSource()).getScene().getWindow();
                scene = FXMLLoader.load((Objects.requireNonNull(getClass().getResource("/wgu/c195pa/customers.fxml"))));
                stage.setScene(new Scene(scene));
                stage.show();
            }
        }
    }

    public void CancelButton(ActionEvent actionEvent) throws IOException {
        stage = (Stage) ((Button) actionEvent.getSource()).getScene().getWindow();
        scene = FXMLLoader.load((Objects.requireNonNull(getClass().getResource("/wgu/c195pa/customers.fxml"))));
        stage.setScene(new Scene(scene));
        stage.show();
    }
    public void stateBoxSelected(MouseEvent mouseEvent) throws SQLException {
        if (countrySelectionBox.getValue() != null) {
            if(countrySelectionBox.getValue().equals("Canada")) {
                stateSelectionBox.setItems(FirstLevelDivisionsDatabaseAccess.getCanadianStates());
            }else if(countrySelectionBox.getValue().equals(("UK"))){
                stateSelectionBox.setItems(FirstLevelDivisionsDatabaseAccess.getUKStates());
            }
            else {
                stateSelectionBox.setItems(FirstLevelDivisionsDatabaseAccess.getUSStates());
            }
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Customers selectedCustomer = getSelectedCustomer();
        if(selectedCustomer != null){
            customerIdField.setText(valueOf(selectedCustomer.getCustomerID()));
            customerNameField.setText(selectedCustomer.getCustomerName());
            customerAddressField.setText(selectedCustomer.getCustomerAddress());
            postalCodeField.setText(selectedCustomer.getPostalCode());
            phoneNumberField.setText(selectedCustomer.getPhoneNumber());
            stateSelectionBox.setValue(selectedCustomer.getDivision());

            try {
                countrySelectionBox.setItems(CountriesDatabaseAccess.getCountriesNameList());
                countrySelectionBox.setValue(Countries.getCountryNameFromCustomerID(selectedCustomer.getDivisionID()));

            } catch (SQLException e) {
                throw new RuntimeException(e);
            }

        }
        else{
            try {
                int customerID = CustomerDatabaseAccess.getCustomers().size();
                customerID += 1;
                customerIdField.setText(valueOf(customerID));
                countrySelectionBox.setItems(CountriesDatabaseAccess.getCountriesNameList());
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }

        }
    }
}
