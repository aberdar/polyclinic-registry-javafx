package ru.aberdar.hospital.controllers;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.FileChooser;
import ru.aberdar.hospital.storage.Doctor;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.zip.DataFormatException;

public class MainWindowController implements Initializable {

    @FXML
    private TableView<Doctor> table;
    @FXML
    private TableColumn<Doctor, String> surnameColumn;
    @FXML
    private TableColumn<Doctor, String> nameColumn;
    @FXML
    private TableColumn<Doctor, String> patronymicColumn;
    @FXML
    private TableColumn<Doctor, String> specialtyColumn;
    @FXML
    private TableColumn<Doctor, String> dayColumn;
    @FXML
    private TableColumn<Doctor, String> timeColumn;
    @FXML
    private TableColumn<Doctor, Integer> cabinetColumn;

    @FXML
    public void openAction() {
        try {
            ObservableList<Doctor> doctors = FXCollections.observableArrayList();
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Open file");
            File file = fileChooser.showOpenDialog(null);
            if (file == null) return;

            BufferedReader bufferedReader = new BufferedReader(new FileReader(file));
            String str;
            while ((str = bufferedReader.readLine()) != null) {
                try {
                    String[] data = str.split(" +");
                    if (str.isEmpty()) break;
                    if (data.length != 7) throw new DataFormatException("Insufficient data.");

                    String surname = data[0];
                    String name = data[1];
                    String patronymic = data[2];
                    String specialty = data[3];
                    String day = data[4];
                    String time = data[5];
                    int cabinet = Integer.parseInt(data[6]);

                    Doctor doctor = new Doctor(
                            surname,
                            name,
                            patronymic,
                            specialty,
                            day,
                            time,
                            cabinet
                    );
                    doctors.add(doctor);
                } catch (DataFormatException exception) {
                    System.out.println(exception.getMessage());
                    bufferedReader.close();
                }
            }
            bufferedReader.close();

            table.setItems(doctors);
        } catch (Exception exception) {
            System.out.println(exception.getMessage());
        }
    }

    @FXML
    public void saveAction() {
        try {
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Save file");
            File file = fileChooser.showSaveDialog(null);
            if (file == null) return;

            FileWriter out = new FileWriter(file);
            for (Doctor doctor : table.getItems()) {
                out.write(
                        doctor.getSurname() + " " +
                        doctor.getName() + " " +
                        doctor.getPatronymic() + " " +
                        doctor.getSpecialty() + " " +
                        doctor.getAdmissionDay() + " " +
                        doctor.getAdmissionTime() + " " +
                        doctor.getCabinetNumber() + "/n"
                );
            }
            out.close();
        } catch (IOException exception) {
            exception.printStackTrace();
        }
    }

    @FXML
    public void exitAction() {
        Platform.exit();
    }

    @FXML
    public void addAction() {
    }

    @FXML
    public void editAction() {
    }

    @FXML
    public void searchAction() {
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        surnameColumn.setCellValueFactory(new PropertyValueFactory<>("surname"));
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        patronymicColumn.setCellValueFactory(new PropertyValueFactory<>("patronymic"));
        specialtyColumn.setCellValueFactory(new PropertyValueFactory<>("specialty"));
        dayColumn.setCellValueFactory(new PropertyValueFactory<>("admissionDay"));
        timeColumn.setCellValueFactory(new PropertyValueFactory<>("admissionTime"));
        cabinetColumn.setCellValueFactory(new PropertyValueFactory<>("cabinetNumber"));
    }

    private void showSelectedError(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText("Please select item");
        alert.setContentText(message);
        alert.showAndWait();
    }
}
