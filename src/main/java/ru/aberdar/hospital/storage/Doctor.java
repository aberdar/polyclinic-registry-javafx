package ru.aberdar.hospital.storage;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Doctor {

    private StringProperty surname;
    private StringProperty name;
    private StringProperty patronymic;
    private StringProperty specialty;
    private StringProperty admissionDay;
    private StringProperty admissionTime;
    private IntegerProperty cabinetNumber;

    public Doctor(
            String surname,
            String name,
            String patronymic,
            String specialty,
            String admissionDay,
            String admissionTime,
            int cabinetNumber
    ) {
        setSurname(surname);
        setName(name);
        setPatronymic(patronymic);
        setSpecialty(specialty);
        setAdmissionDay(admissionDay);
        setAdmissionTime(admissionTime);
        setCabinetNumber(cabinetNumber);
    }

    private StringProperty surnameStringProperty() {
        if (surname == null) {
            surname = new SimpleStringProperty();
        }
        return surname;
    }

    private StringProperty nameStringProperty() {
        if (name == null) {
            name = new SimpleStringProperty();
        }
        return name;
    }

    private StringProperty patronymicStringProperty() {
        if (patronymic == null) {
            patronymic = new SimpleStringProperty();
        }
        return patronymic;
    }

    private StringProperty specialtyStringProperty() {
        if (specialty == null) {
            specialty = new SimpleStringProperty();
        }
        return specialty;
    }

    private StringProperty admissionDayStringProperty() {
        if (admissionDay == null) {
            admissionDay = new SimpleStringProperty();
        }
        return admissionDay;
    }

    private StringProperty admissionTimeStringProperty() {
        if (admissionTime == null) {
            admissionTime = new SimpleStringProperty();
        }
        return admissionTime;
    }

    private IntegerProperty cabinetNumberIntegerProperty() {
        if (cabinetNumber == null) {
            cabinetNumber = new SimpleIntegerProperty();
        }
        return cabinetNumber;
    }

    public void setSurname(String surname) {
        surnameStringProperty().set(surname);
    }

    public void setName(String name) {
        nameStringProperty().set(name);
    }

    public void setPatronymic(String patronymic) {
        patronymicStringProperty().set(patronymic);
    }

    public void setSpecialty(String specialty) {
        specialtyStringProperty().set(specialty);
    }

    public void setAdmissionDay(String day) {
        admissionDayStringProperty().set(day);
    }

    public void setAdmissionTime(String time) {
        admissionTimeStringProperty().set(time);
    }

    public void setCabinetNumber(int cabinetNumber) {
        cabinetNumberIntegerProperty().set(cabinetNumber);
    }

    public String getSurname() {
        return surnameStringProperty().get();
    }

    public String getName() {
        return nameStringProperty().get();
    }

    public String getPatronymic() {
        return patronymicStringProperty().get();
    }

    public String getSpecialty() {
        return specialtyStringProperty().get();
    }

    public String getAdmissionDay() {
        return admissionDayStringProperty().get();
    }

    public String getAdmissionTime() {
        return admissionTimeStringProperty().get();
    }

    public int getCabinetNumber() {
        return cabinetNumberIntegerProperty().get();
    }
}
