package ru.aberdar.hospital.controllers;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ChoiceBox;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

public class SearchBySpecialtyController implements Initializable {
    @FXML
    public ChoiceBox<String> search;

    private ButtonType buttonType = ButtonType.CANCEL;
    private Stage stage;
    private String searchSpecialty;

    private final String[] specialtyData = {"Allergist", "Anesthesiologist", "Cardiologist", "Dentist", "Geneticist"};

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    public ButtonType getButtonType() {
        return buttonType;
    }

    public String getSearchSpecialty() {
        return searchSpecialty;
    }

    @FXML
    public void handleOk() {
        searchSpecialty = search.getSelectionModel().getSelectedItem();
        buttonType = ButtonType.OK;
        stage.close();
    }

    @FXML
    public void handleCancel() {
        buttonType = ButtonType.CANCEL;
        stage.close();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        search.getItems().addAll(specialtyData);
        search.setValue(specialtyData[0]);
    }
}
