package wgu.c195pa.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;


public class HomeController {

    public AnchorPane homePane;
    Stage stage;
    Parent scene;
    public void viewAppointments(ActionEvent actionEvent) throws IOException {
        stage = (Stage) ((Button)actionEvent.getSource()).getScene().getWindow();
        scene = FXMLLoader.load((Objects.requireNonNull(getClass().getResource("/wgu/c195pa/appointments.fxml"))));
        stage.setScene(new Scene(scene));
        stage.show();
    }

    public void viewCustomers(ActionEvent actionEvent) throws IOException {
        stage = (Stage) ((Button)actionEvent.getSource()).getScene().getWindow();
        scene = FXMLLoader.load((Objects.requireNonNull(getClass().getResource("/wgu/c195pa/customers.fxml"))));
        stage.setScene(new Scene(scene));
        stage.show();
    }

    public void viewReports(ActionEvent actionEvent) throws IOException {
        stage = (Stage) ((Button)actionEvent.getSource()).getScene().getWindow();
        scene = FXMLLoader.load((Objects.requireNonNull(getClass().getResource("/wgu/c195pa/reports.fxml"))));
        stage.setScene(new Scene(scene));
        stage.show();
    }

    public void logoutButton(ActionEvent actionEvent) throws IOException {
        stage = (Stage) ((Button)actionEvent.getSource()).getScene().getWindow();
        scene = FXMLLoader.load((Objects.requireNonNull(getClass().getResource("/wgu/c195pa/login.fxml"))));
        stage.setScene(new Scene(scene));
        stage.show();
    }
}
