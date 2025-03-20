package wgu.c195pa;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import wgu.c195pa.helper.JDBC;

import java.io.IOException;

public class Main extends Application {
    public static void main(String[] args) {
        JDBC.getConnection();
//        Locale.setDefault(new Locale("fr","FR"));
        launch(args);
        JDBC.closeConnection();
    }

    @Override
    public void start(Stage stage)  {
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("/wgu/c195pa/login.fxml"));
        try {
            Scene scene = new Scene(fxmlLoader.load());
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}