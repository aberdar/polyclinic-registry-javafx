package ru.aberdar.hospital.controllers;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
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

    private final String[] specialtyData = {"Allergist", "Anesthesiologist", "Cardiologist", "Dentist", "Geneticist"};
    private final String[] dayData = {"Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday", "Sunday"};
    private final String[] timeData = {"From 8 to 13", "From 14 to 19"};

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    public void setDoctor(Doctor doctor) {
        this.doctor = doctor;
    }

    public ButtonType getButtonType() {
        return buttonType;
    }

    private boolean isInputValid() {
        if (addSurname.getText() == null ||
                addSurname.getText().length() == 0 ||
                !Character.isUpperCase(addSurname.getText().charAt(0)) ||
                !addSurname.getText().matches("[a-zA-Z]+")
        ) {
            errorMessage += "No valid surname.\n";
        }

        if (addName.getText() == null ||
                addName.getText().length() == 0 ||
                !Character.isUpperCase(addName.getText().charAt(0)) ||
                !addName.getText().matches("[a-zA-Z]+")
        ) {
            errorMessage += "No valid name.\n";
        }

        if (addPatronymic.getText() == null ||
                addPatronymic.getText().length() == 0 ||
                !Character.isUpperCase(addPatronymic.getText().charAt(0)) ||
                !addPatronymic.getText().matches("[a-zA-Z]+")
        ) {
            errorMessage += "No valid patronymic.\n";
        }

        return errorMessage.length() == 0;
    }

    @FXML
    public void handleOk() {
        if (isInputValid()) {
            doctor.setSurname(addSurname.getText());
            doctor.setName(addName.getText());
            doctor.setPatronymic(addPatronymic.getText());
            doctor.setSpecialty(addSpecialty.getSelectionModel().getSelectedItem());
            doctor.setAdmissionDay(addDay.getSelectionModel().getSelectedItem());
            doctor.setAdmissionTime(addTime.getSelectionModel().getSelectedItem());
            doctor.setCabinetNumber(addCabinet.getValue());

            buttonType = ButtonType.OK;
            stage.close();
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.initOwner(stage);
            alert.setTitle("Error");
            alert.setHeaderText("Please correct invalid fields");
            alert.setContentText(errorMessage);
            alert.showAndWait();
            errorMessage = "";
        }
    }

    @FXML
    public void handleCancel() {
        buttonType = ButtonType.CANCEL;
        stage.close();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        addSpecialty.getItems().addAll(specialtyData);
        addSpecialty.setValue(specialtyData[0]);

        addDay.getItems().addAll(dayData);
        addDay.setValue(dayData[0]);

        addTime.getItems().addAll(timeData);
        addTime.setValue(timeData[0]);
    }
}
