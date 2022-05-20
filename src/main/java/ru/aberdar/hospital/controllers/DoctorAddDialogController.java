package ru.aberdar.hospital.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Spinner;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import ru.aberdar.hospital.storage.Doctor;

import java.net.URL;
import java.util.ResourceBundle;

public class DoctorAddDialogController implements Initializable {

    @FXML
    public TextField addSurname;
    @FXML
    public TextField addName;
    @FXML
    public TextField addPatronymic;
    @FXML
    public ChoiceBox<String> addSpecialty;
    @FXML
    public ChoiceBox<String> addDay;
    @FXML
    public ChoiceBox<String> addTime;
    @FXML
    public Spinner<Integer> addCabinet;

    private Stage stage;
    private Doctor doctor;
    private ButtonType buttonType = ButtonType.CANCEL;
    private String errorMessage = "";

    @FXML
    public void handleOk() {
    }

    @FXML
    public void handleCancel() {
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
    }
}
