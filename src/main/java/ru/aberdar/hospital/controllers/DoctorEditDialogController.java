package ru.aberdar.hospital.controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
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

public class DoctorEditDialogController implements Initializable {

    @FXML
    public TextField editSurname;
    @FXML
    public TextField editName;
    @FXML
    public TextField editPatronymic;
    @FXML
    public ChoiceBox<String> editSpecialty;
    @FXML
    public ChoiceBox<String> editDay;
    @FXML
    public ChoiceBox<String> editTime;
    @FXML
    public Spinner<Integer> editCabinet;

    private Stage stage;
    private Doctor doctor;
    private ButtonType buttonType = ButtonType.CANCEL;
    private String errorMessage = "";

    private final ObservableList<Doctor> doctorsData = FXCollections.observableArrayList();
    private final String[] specialtyData = {"Allergist", "Anesthesiologist", "Cardiologist", "Dentist", "Geneticist"};
    private final String[] dayData = {"Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday", "Sunday"};
    private final String[] timeData = {"From 8 to 13", "From 14 to 19"};

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    public void setDoctor(Doctor doctor) {
        this.doctor = doctor;

        editSurname.setText(this.doctor.getSurname());
        editName.setText(this.doctor.getName());
        editPatronymic.setText(this.doctor.getPatronymic());
        editSpecialty.setValue(this.doctor.getSpecialty());
        editDay.setValue(this.doctor.getAdmissionDay());
        editTime.setValue(this.doctor.getAdmissionTime());
        editCabinet.getValueFactory().setValue(this.doctor.getCabinetNumber());
    }

    public void setDoctorsData(ObservableList<Doctor> data) {
        this.doctorsData.setAll(data);
    }

    public ButtonType getButtonType() {
        return buttonType;
    }

    private boolean isInputValid() {
        if (editSurname.getText() == null ||
                editSurname.getText().length() == 0 ||
                !Character.isUpperCase(editSurname.getText().charAt(0)) ||
                !editSurname.getText().matches("[a-zA-Z]+")
        ) {
            errorMessage += "No valid surname.\n";
        }

        if (editName.getText() == null ||
                editName.getText().length() == 0 ||
                !Character.isUpperCase(editName.getText().charAt(0)) ||
                !editName.getText().matches("[a-zA-Z]+")
        ) {
            errorMessage += "No valid name.\n";
        }

        if (editPatronymic.getText() == null ||
                editPatronymic.getText().length() == 0 ||
                !Character.isUpperCase(editPatronymic.getText().charAt(0)) ||
                !editPatronymic.getText().matches("[a-zA-Z]+")
        ) {
            errorMessage += "No valid patronymic.\n";
        }

        if (!checkingTimeInterval()) {
            errorMessage += "Incorrect working time interval.\n";
        }

        if (!checkingNumberOfWorkingDays()) {
            errorMessage += "The number of working days per week exceeds five\n";
        }

        if (!checkingDuplicateCabinet()) {
            errorMessage += "Incorrect cabinet number.\n";
        }

        return errorMessage.length() == 0;
    }

    /**
     * Checking for work in two shifts
     * @return false if it has not passed the verification
     */
    private boolean checkingTimeInterval() {
        for (Doctor element: doctorsData) {
            if (!foundDoctor(element) &&
                element.getAdmissionDay().equals(editDay.getSelectionModel().getSelectedItem()) &&
                element.getAdmissionTime() != null
            ) {
                return false;
            }
        }
        return true;
    }

    /**
     * Checking for a five-day working week
     * @return false if it has not passed the verification
     */
    private boolean checkingNumberOfWorkingDays() {
        String workingDay = "";
        for (Doctor element: doctorsData) {
            if (foundDoctor(element) &&
                    !workingDay.contains(element.getAdmissionDay())) {
                workingDay += element.getAdmissionDay() + " ";
            }
        }

        if (!workingDay.contains(editDay.getSelectionModel().getSelectedItem())) {
            workingDay += editDay.getSelectionModel().getSelectedItem() + " ";
        }

        return workingDay.split(" ").length <= 5;
    }

    /**
     * Checking for duplicate cabinet
     * @return false if it has not passed the verification
     */
    private boolean checkingDuplicateCabinet() {
        for (Doctor element: doctorsData) {
            if (element.getAdmissionDay().equals(editDay.getSelectionModel().getSelectedItem()) &&
                    element.getAdmissionTime().equals(editTime.getSelectionModel().getSelectedItem()) &&
                    element.getCabinetNumber() == editCabinet.getValue()
            ) {
                return false;
            }
        }
        return true;
    }

    /**
     * Search for the appropriate doctor
     * @param searchingDoctor - doctor to check compliance
     * @return true if doctor matches
     */
    private boolean foundDoctor(Doctor searchingDoctor) {
        return searchingDoctor.getName().equals(editName.getText()) &&
                searchingDoctor.getSurname().equals(editSurname.getText()) &&
                searchingDoctor.getPatronymic().equals(editPatronymic.getText());
    }


    @FXML
    public void handleOk() {
        if (isInputValid()) {
            doctor.setSurname(editSurname.getText());
            doctor.setName(editName.getText());
            doctor.setPatronymic(editPatronymic.getText());
            doctor.setSpecialty(editSpecialty.getSelectionModel().getSelectedItem());
            doctor.setAdmissionDay(editDay.getSelectionModel().getSelectedItem());
            doctor.setAdmissionTime(editTime.getSelectionModel().getSelectedItem());
            doctor.setCabinetNumber(editCabinet.getValue());

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
        editSpecialty.getItems().addAll(specialtyData);
        editDay.getItems().addAll(dayData);
        editTime.getItems().addAll(timeData);
    }
}
