package wgu.c195pa.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import wgu.c195pa.dao.AppointmentDatabaseAccess;
import wgu.c195pa.dao.UserDatabaseAccess;
import wgu.c195pa.model.Appointments;
import wgu.c195pa.model.Users;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URL;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.util.Locale;
import java.util.Objects;
import java.util.ResourceBundle;


public class LoginController implements Initializable {
    static String currentUser = null;
    public Label userNameText;
    public Label passwordText;
    public Button loginButton;
    public Button exitButton;
    public Label loginText;
    Stage stage;
    Parent scene;
    public AnchorPane loginPane;
    public TextField usernameField;
    public TextField passwordField;
    public Label locationText;
    private ResourceBundle rb = ResourceBundle.getBundle("Nat", Locale.getDefault());

    public void loginButton(ActionEvent actionEvent) throws SQLException, IOException {
        FileWriter fileWriter = new FileWriter("login_activity.txt", true);
        PrintWriter outputFile = new PrintWriter(fileWriter);
        String inputName = usernameField.getText();

        if(loginAuthentication()){
            outputFile.println("Successful login by user: " + inputName + " at: " + Timestamp.valueOf(LocalDateTime.now()) + ".");
            currentUser = usernameField.getText();

            ObservableList<Appointments> appointmentsList = FXCollections.observableArrayList();
            try {
                appointmentsList = AppointmentDatabaseAccess.getAppointmentsFrom15Mins();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
            if (appointmentsList.isEmpty()){
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("");
                alert.setHeaderText("");
                alert.setContentText("There are no upcoming appointments in the next fifteen minutes");
                alert.showAndWait();
            }
            else {
                for (Appointments appointment : appointmentsList){
                    int appointmentID = appointment.getAppointmentID();
                    LocalDateTime localDateTime = appointment.getStart().toLocalDateTime();
                    LocalDate date = localDateTime.toLocalDate();
                    LocalTime time = localDateTime.toLocalTime();

                    Alert alert = new Alert(Alert.AlertType.WARNING);
                    alert.setTitle("");
                    alert.setHeaderText("");
                    alert.setContentText("There is an upcoming appointment.\n" +
                            "AppointmentID: " + appointmentID + "\nDate: " + date + "\nTime: "+ time);
                    alert.showAndWait();
                }
            }


            stage = (Stage) ((Button)actionEvent.getSource()).getScene().getWindow();
            scene = FXMLLoader.load((Objects.requireNonNull(getClass().getResource("/wgu/c195pa/home.fxml"))));
            stage.setScene(new Scene(scene));
            stage.show();
        }
        else {
            outputFile.println("Failed login by user: " + inputName + " at: " + Timestamp.valueOf(LocalDateTime.now()) + ".");

            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText(rb.getString("LoginError"));
            alert.showAndWait();
        }
        outputFile.close();
    }
    public static String getCurrentUser(){
        return currentUser;
    }

    public void exitButton(ActionEvent actionEvent) {
        stage = (Stage) loginPane.getScene().getWindow();
        stage.close();
    }

    public boolean loginAuthentication() throws SQLException, IOException {
        String inputName = usernameField.getText();
        String inputPassword = passwordField.getText();
        ObservableList<Users> loginUsers = UserDatabaseAccess.getUsers();


        for (Users user : loginUsers) {
            if (Objects.equals(inputName, user.getUserName()) && Objects.equals(inputPassword, user.getUserPassword())) {
                return true;
            }
        }
        return false;

    }
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle){
        loginText.setText(rb.getString("Login"));
        userNameText.setText(rb.getString("Username"));
        passwordText.setText(rb.getString("Password"));
        loginButton.setText(rb.getString("Enter"));
        exitButton.setText(rb.getString("Exit"));
        ZoneId location = ZoneId.systemDefault();
        locationText.setText("Location:  " + location);
    }
}
