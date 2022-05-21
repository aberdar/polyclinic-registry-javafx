package ru.aberdar.hospital.controllers;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ChoiceBox;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

public class SearchByDayDialogController implements Initializable {
    @FXML
    public ChoiceBox<String> search;

    private ButtonType buttonType = ButtonType.CANCEL;
    private Stage stage;
    private String searchDay;

    private final String[] dayData = {"Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday", "Sunday"};

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    public ButtonType getButtonType() {
        return buttonType;
    }

    public String getSearchDay() {
        return searchDay;
    }

    @FXML
    public void handleOk() {
        searchDay = search.getSelectionModel().getSelectedItem();
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
        search.getItems().addAll(dayData);
        search.setValue(dayData[0]);
    }
}
