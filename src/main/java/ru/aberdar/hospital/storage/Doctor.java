package ru.aberdar.hospital.storage;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.zip.DataFormatException;

public class Doctor {

    private StringProperty surname;
    private StringProperty name;
    private StringProperty patronymic;
    private StringProperty specialty;
    private StringProperty admissionDay;
    private StringProperty admissionTime;
    private IntegerProperty cabinetNumber;

    private final Set<String> dayData = new HashSet<>(List.of(new String[]{
            "Monday",
            "Tuesday",
            "Wednesday",
            "Thursday",
            "Friday",
            "Saturday",
            "Sunday"
    }));
    private final Set<String> timeData = new HashSet<>(List.of(new String[]{
            "From 8 to 13",
            "From 14 to 19"
    }));
    private final Set<String> specialtyData = new HashSet<>(List.of(new String[]{
            "Allergist",
            "Anesthesiologist",
            "Cardiologist",
            "Dentist",
            "Geneticist"
    }));

    public Doctor(
            String surname,
            String name,
            String patronymic,
            String specialty,
            String admissionDay,
            String admissionTime,
            int cabinetNumber
    ) throws DataFormatException {
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

    public void setSurname(String surname) throws DataFormatException {
        if (!Character.isUpperCase(surname.charAt(0)) ||
            !surname.matches("[a-zA-Z]+")) {
            throw new DataFormatException("No valid surname.");
        }
        surnameStringProperty().set(surname);
    }

    public void setName(String name) throws DataFormatException {
        if (!Character.isUpperCase(name.charAt(0)) ||
            !name.matches("[a-zA-Z]+")) {
            throw new DataFormatException("No valid name.");
        }
        nameStringProperty().set(name);
    }

    public void setPatronymic(String patronymic) throws DataFormatException {
        if (!Character.isUpperCase(patronymic.charAt(0)) ||
            !patronymic.matches("[a-zA-Z]+")) {
            throw new DataFormatException("No valid patronymic.");
        }
        patronymicStringProperty().set(patronymic);
    }

    public void setSpecialty(String specialty) throws DataFormatException {
        if (!specialtyData.contains(specialty)) {
            throw new DataFormatException("No valid specialty.");
        }
        specialtyStringProperty().set(specialty);
    }

    public void setAdmissionDay(String day) throws DataFormatException {
        if (!dayData.contains(day)) {
            throw new DataFormatException("No valid day.");
        }
        admissionDayStringProperty().set(day);
    }

    public void setAdmissionTime(String time) throws DataFormatException {
        if (!timeData.contains(time)) {
            throw new DataFormatException("No valid time.");
        }
        admissionTimeStringProperty().set(time);
    }

    public void setCabinetNumber(int cabinetNumber) throws DataFormatException {
        if (cabinetNumber < 1) {
            throw new DataFormatException("No valid cabinet number.");
        }
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
