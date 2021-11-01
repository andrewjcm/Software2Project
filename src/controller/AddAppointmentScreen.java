package controller;

import dao.AppointmentsDao;
import dao.ContactsDao;
import dao.CustomersDao;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.stage.Stage;
import model.*;
import utils.alerts.Confirm;
import utils.alerts.Schedule;
import utils.auth.UserAuth;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Optional;
import java.util.ResourceBundle;

public class AddAppointmentScreen implements Initializable {
    public Button appointmentsButton;
    public Button customersButton;
    public Button reportsButton;
    public Button logoutButton;
    public Button saveAppointmentButton;
    public Button cancelAppointmentButton;
    public TextField appointmentIdTextField;
    public TextField appointmentTitleNameTextField;
    public TextField appointmentDescriptionTextField;
    public TextField appointmentLocationTextField;
    public TextField appointmentTypeTextField;
    public TextField appointmentCreateDateTextField;
    public TextField appointmentCreatedByTextField;
    public TextField appointmentLastUpdateTextField;
    public TextField appointmentUpdatedByTextField;
    public ComboBox<LocalTime> startComboBox;
    public ComboBox<LocalTime> endComboBox;
    public ComboBox<Customer> customerComboBox;
    public ComboBox<Contact> contactComboBox;
    public DatePicker datePicker;


    public static String previousScreen;
    public static Customer custFootball;

    public static void setPreviousScreen(String prevScreen) { previousScreen = prevScreen; }

    public static void setCustFootball(Customer customer) { custFootball = customer; }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        LocalDateTime now = LocalDateTime.now();
        String userName = UserAuth.getLoggedInUser().toString();
        appointmentCreateDateTextField.setText(now.toString());
        appointmentCreatedByTextField.setText(userName);
        appointmentLastUpdateTextField.setText(now.toString());
        appointmentUpdatedByTextField.setText(userName);

        datePicker.setValue(LocalDate.now());
        startComboBox.setItems(Hours.getStartTimes());
        endComboBox.setItems(Hours.getEndTimes());
        customerComboBox.setItems(CustomersDao.getAllCustomers());
        contactComboBox.setItems(ContactsDao.getAllContacts());

        if (custFootball != null)
            customerComboBox.setValue(custFootball);

    }

    public void onAppointmentsButton(ActionEvent actionEvent) throws IOException {
        Stage stage = (Stage) appointmentsButton.getScene().getWindow();
        if (noUserInput()) {
            GlobalController.viewAppointmentScreen(stage);
        }
        else {
            Optional<ButtonType> userResponse = Confirm.cancel();
            if (userResponse.isPresent() && userResponse.get() == ButtonType.OK) {
                GlobalController.viewAppointmentScreen(stage);
            }
        }
    }

    public void onCustomersButton(ActionEvent actionEvent) throws IOException {
        Stage stage = (Stage) customersButton.getScene().getWindow();
        if (noUserInput()) {
            GlobalController.viewCustomerScreen(stage);
        }
        else {
            Optional<ButtonType> userResponse = Confirm.cancel();
            if (userResponse.isPresent() && userResponse.get() == ButtonType.OK) {
                GlobalController.viewCustomerScreen(stage);
            }
        }
    }

    public void onReportsButton(ActionEvent actionEvent) throws IOException {
        Stage stage = (Stage) reportsButton.getScene().getWindow();
        if (noUserInput()) {
            GlobalController.reportsScreen(stage);
        }
        else {
            Optional<ButtonType> userResponse = Confirm.cancel();
            if (userResponse.isPresent() && userResponse.get() == ButtonType.OK) {
                GlobalController.reportsScreen(stage);
            }
        }
    }

    public void onLogoutButton(ActionEvent actionEvent) throws IOException {
        Stage stage = (Stage) logoutButton.getScene().getWindow();
        if (noUserInput()) {
            GlobalController.loginScreen(stage);
        }
        else {
            Optional<ButtonType> userResponse = Confirm.cancel();
            if (userResponse.isPresent() && userResponse.get() == ButtonType.OK) {
                GlobalController.loginScreen(stage);
            }
        }
    }

    public void onSaveAppointmentButton(ActionEvent actionEvent) throws IOException, SQLException {
        LocalDateTime apptStart = LocalDateTime.of(datePicker.getValue(),
                startComboBox.getSelectionModel().getSelectedItem());
        LocalDateTime apptEnd = LocalDateTime.of(datePicker.getValue(),
                endComboBox.getSelectionModel().getSelectedItem());

        // Validate that new appointment does not overlap an existing appointment.
        if (!AppointmentsDao.openAppointmentTime(apptStart, apptEnd)){
                Schedule.overlap();
        }
        // Validate that appointment is scheduled within biz hours.
        else if (!Hours.getStartTimes().contains(apptStart.toLocalTime()) &&
        !Hours.getEndTimes().contains(apptEnd.toLocalTime())){
            Schedule.outsideBizHours();
        }
        else {
            AppointmentsDao.addAppointment(
                    new Appointment(
                            AppointmentsDao.getIncrementedApptId(),
                            appointmentTitleNameTextField.getText(),
                            appointmentDescriptionTextField.getText(),
                            appointmentLocationTextField.getText(),
                            appointmentTypeTextField.getText(),
                            apptStart,
                            apptEnd,
                            LocalDateTime.now(),
                            UserAuth.getLoggedInUser().toString(),
                            LocalDateTime.now(),
                            UserAuth.getLoggedInUser().toString(),
                            customerComboBox.getSelectionModel().getSelectedItem(),
                            UserAuth.getLoggedInUser(),
                            contactComboBox.getSelectionModel().getSelectedItem()
                    )
            );

            Stage stage = (Stage) saveAppointmentButton.getScene().getWindow();

            if (previousScreen != null && previousScreen.equals("ViewCustomerScreen"))
                GlobalController.viewCustomerScreen(stage);
            else
                GlobalController.viewAppointmentScreen(stage);

            previousScreen = null;
            custFootball = null;
        }
    }

    public void cancelAppointmentButton(ActionEvent actionEvent) throws IOException {
        Stage stage = (Stage) cancelAppointmentButton.getScene().getWindow();
        if (noUserInput()) {
            GlobalController.viewAppointmentScreen(stage);
        }
        else {
            Optional<ButtonType> userResponse = Confirm.cancel();
            if (userResponse.isPresent() && userResponse.get() == ButtonType.OK) {
                GlobalController.viewAppointmentScreen(stage);
            }
        }
    }

    public void onStartCombo(ActionEvent actionEvent) {
        LocalTime startTime = startComboBox.getSelectionModel().getSelectedItem();
        if (startTime != null)
            endComboBox.setItems(Hours.getEndTimes(startTime));
        else
            endComboBox.setItems(Hours.getEndTimes());
    }

    public ArrayList<TextField> getTextFieldList() {
        ArrayList<TextField> textFieldList = new ArrayList<>();
        textFieldList.add(appointmentTitleNameTextField);
        textFieldList.add(appointmentDescriptionTextField);
        textFieldList.add(appointmentLocationTextField);
        textFieldList.add(appointmentTypeTextField);

        return textFieldList;
    }

    public boolean noUserInput() {
        for (TextField tf: getTextFieldList()){
            if (!tf.getText().equals(""))
                return false;
        }
        return true;
    }
}
