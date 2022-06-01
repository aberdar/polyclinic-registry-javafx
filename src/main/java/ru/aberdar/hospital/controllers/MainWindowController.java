package ru.aberdar.hospital.controllers;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import ru.aberdar.hospital.storage.Doctor;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.stream.Collectors;
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

    private final ObservableList<Doctor> doctors = FXCollections.observableArrayList();

    @FXML
    public void openAction() {
        try {
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
                    if (data.length != 10) throw new DataFormatException("Insufficient data.");

                    String surname = data[0];
                    String name = data[1];
                    String patronymic = data[2];
                    String specialty = data[3];
                    String day = data[4];
                    String time = data[5] + " " + data[6] + " " +  data[7] + " " + data[8];
                    int cabinet = Integer.parseInt(data[9]);

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
                        doctor.getCabinetNumber() + "\n"
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
        URL url = getClass().getResource("/ru.aberdar.hospital/DoctorAddDialog.fxml");
        FXMLLoader loader = new FXMLLoader(url);

        try {
            Parent root = loader.load();
            DoctorAddDialogController controller = loader.getController();

            Stage stage = new Stage();
            Scene scene = new Scene(root);
            Doctor newDoctor = new Doctor("", "", "", "", "", "", 0);
            stage.setTitle("Add doctor");
            stage.setScene(scene);
            controller.setStage(stage);
            controller.setDoctor(newDoctor);
            controller.setDoctorsData(doctors);
            stage.showAndWait();

            if (controller.getButtonType() == ButtonType.OK) {
                doctors.add(newDoctor);
                table.setItems(doctors);
            }
        } catch (IOException exception) {
            exception.printStackTrace();
        }
    }

    @FXML
    public void editAction() {
        URL url = getClass().getResource("/ru.aberdar.hospital/DoctorEditDialog.fxml");
        FXMLLoader loader = new FXMLLoader(url);
        Doctor doctor = table.getSelectionModel().getSelectedItem();

        if (doctor != null) {
            try {
                Parent root = loader.load();
                DoctorEditDialogController controller = loader.getController();

                Stage stage = new Stage();
                Scene scene = new Scene(root);
                stage.setTitle("Edit doctor");
                stage.setScene(scene);
                controller.setDoctor(doctor);
                controller.setStage(stage);
                stage.showAndWait();

                if (controller.getButtonType() == ButtonType.OK) {
                    table.refresh();
                }
            } catch (IOException exception) {
                exception.printStackTrace();
            }
        } else {
            showSelectedError("No edit items.");
        }
    }

    @FXML
    public void searchBySpecialtyAction() {
        URL url = getClass().getResource("/ru.aberdar.hospital/SearchBySpecialtyDialog.fxml");
        FXMLLoader loader = new FXMLLoader(url);

        try {
            Parent root = loader.load();
            SearchBySpecialtyController controller = loader.getController();

            Stage stage = new Stage();
            Scene scene = new Scene(root);
            stage.setTitle("Search by specialty");
            stage.setScene(scene);
            controller.setStage(stage);
            stage.showAndWait();

            if (controller.getButtonType() == ButtonType.OK) {
                String searchSpecialty = controller.getSearchSpecialty();

                ObservableList<Doctor> sortedData = FXCollections.observableArrayList();
                sortedData.setAll(doctors.stream()
                        .filter(element -> element.getSpecialty().equals(searchSpecialty))
                        .collect(Collectors.toList())
                );
                showDialogWithSearchBySpecialty(sortedData);

                stage.close();
            }

        } catch (IOException exception) {
            exception.printStackTrace();
        }
    }

    private void showDialogWithSearchBySpecialty(ObservableList<Doctor> sortedData) {
        URL url = getClass().getResource("/ru.aberdar.hospital/ShowSearchBySpecialtyDialog.fxml");
        FXMLLoader loader = new FXMLLoader(url);

        try {
            Parent root = loader.load();
            ShowSearchBySpecialtyController controller = loader.getController();

            Stage stage = new Stage();
            Scene scene = new Scene(root);
            stage.setTitle("Search by specialty");
            stage.setScene(scene);
            controller.setDoctorsData(sortedData);
            stage.show();
        } catch (IOException exception) {
            exception.printStackTrace();
        }
    }

    @FXML
    public void searchByDay() {
        URL url = getClass().getResource("/ru.aberdar.hospital/SearchByDayDialog.fxml");
        FXMLLoader loader = new FXMLLoader(url);

        try {
            Parent root = loader.load();
            SearchByDayDialogController controller = loader.getController();

            Stage stage = new Stage();
            Scene scene = new Scene(root);
            stage.setTitle("Search by day");
            stage.setScene(scene);
            controller.setStage(stage);
            stage.showAndWait();

            if (controller.getButtonType() == ButtonType.OK) {
                String searchDay = controller.getSearchDay();

                ObservableList<Doctor> sortedData = FXCollections.observableArrayList();
                sortedData.setAll(doctors.stream()
                        .filter(element -> element.getAdmissionDay().equals(searchDay))
                        .collect(Collectors.toList())
                );
                table.setItems(sortedData);

                stage.close();
            }

        } catch (IOException exception) {
            exception.printStackTrace();
        }
    }

    @FXML
    public void refreshTable() {
        table.setItems(doctors);
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
