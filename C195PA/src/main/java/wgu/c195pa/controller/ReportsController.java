package wgu.c195pa.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;


public class ReportsController implements Initializable {
    public ComboBox<String> reportsBox;
    Stage stage;
    Parent scene;

    public void selectReport(ActionEvent actionEvent) throws IOException {
        String selectedReport = reportsBox.getValue();
        if (selectedReport.contains("Month")) {
            stage = (Stage) ((ComboBox<?>) actionEvent.getSource()).getScene().getWindow();
            scene = FXMLLoader.load((Objects.requireNonNull(getClass().getResource("/wgu/c195pa/apptMonthTypeReport.fxml"))));
            stage.setScene(new Scene(scene));
            stage.show();
        }
        else if(selectedReport.contains("Contact")){
            stage = (Stage) ((ComboBox<?>) actionEvent.getSource()).getScene().getWindow();
            scene = FXMLLoader.load((Objects.requireNonNull(getClass().getResource("/wgu/c195pa/apptContactReport.fxml"))));
            stage.setScene(new Scene(scene));
            stage.show();
        }
        else {
            stage = (Stage) ((ComboBox<?>) actionEvent.getSource()).getScene().getWindow();
            scene = FXMLLoader.load((Objects.requireNonNull(getClass().getResource("/wgu/c195pa/customersByCountryReport.fxml"))));
            stage.setScene(new Scene(scene));
            stage.show();
        }
    }

    public void backButton(ActionEvent actionEvent) throws IOException {
        stage = (Stage) ((Button) actionEvent.getSource()).getScene().getWindow();
        scene = FXMLLoader.load((Objects.requireNonNull(getClass().getResource("/wgu/c195pa/home.fxml"))));
        stage.setScene(new Scene(scene));
        stage.show();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        ObservableList<String> reportsList = FXCollections.observableArrayList();
        reportsList.add("Appointments by Month and Type");
        reportsList.add("Schedule for Contacts");
        reportsList.add("Customers by Country");
        reportsBox.setItems(reportsList);
    }
}
