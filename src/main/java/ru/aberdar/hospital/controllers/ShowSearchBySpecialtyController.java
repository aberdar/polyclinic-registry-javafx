package ru.aberdar.hospital.controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import ru.aberdar.hospital.storage.Doctor;

import java.net.URL;
import java.util.ResourceBundle;

public class ShowSearchBySpecialtyController implements Initializable {

    @FXML
    private TableView<Doctor> table;
    @FXML
    private TableColumn<Doctor, String> surnameColumn;
    @FXML
    private TableColumn<Doctor, String> nameColumn;
    @FXML
    private TableColumn<Doctor, String> patronymicColumn;
    @FXML
    private TableColumn<Doctor, String> dayColumn;
    @FXML
    private TableColumn<Doctor, String> timeColumn;
    @FXML
    private TableColumn<Doctor, Integer> cabinetColumn;

    public void setDoctorsData(ObservableList<Doctor> data) {
       table.setItems(data);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        surnameColumn.setCellValueFactory(new PropertyValueFactory<>("surname"));
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        patronymicColumn.setCellValueFactory(new PropertyValueFactory<>("patronymic"));
        dayColumn.setCellValueFactory(new PropertyValueFactory<>("admissionDay"));
        timeColumn.setCellValueFactory(new PropertyValueFactory<>("admissionTime"));
        cabinetColumn.setCellValueFactory(new PropertyValueFactory<>("cabinetNumber"));
    }
}
