package ru.aberdar.hospital;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import ru.aberdar.hospital.controllers.MainWindowController;

import java.io.IOException;
import java.net.URL;

public class HospitalApp extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        URL url = getClass().getResource("/ru.aberdar.hospital/MainWindow.fxml");
        FXMLLoader loader = new FXMLLoader(url);

        try {
            Parent root = loader.load();
            MainWindowController controller = loader.getController();

            Scene scene = new Scene(root);
            primaryStage.setTitle("Make an appointment");
            primaryStage.setScene(scene);
            primaryStage.show();
        } catch (IOException exception) {
            exception.printStackTrace();
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}
