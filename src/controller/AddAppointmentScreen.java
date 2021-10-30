package controller;

import dao.AppointmentsDao;
import dao.ContactsDao;
import dao.CustomersDao;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import model.*;
import utils.auth.UserAuth;
import utils.time.ZoneLocalize;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
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

    }

    public void onAppointmentsButton(ActionEvent actionEvent) throws IOException {

        // TODO: Add confirm alert


        Stage stage = (Stage) appointmentsButton.getScene().getWindow();
        GlobalController.viewAppointmentScreen(stage);
    }

    public void onCustomersButton(ActionEvent actionEvent) throws IOException {

        // TODO: Add confirm alert

        Stage stage = (Stage) customersButton.getScene().getWindow();
        GlobalController.viewCustomerScreen(stage);
    }

    public void onReportsButton(ActionEvent actionEvent) throws IOException {

        // TODO: Add confirm alert

        Stage stage = (Stage) reportsButton.getScene().getWindow();
        GlobalController.reportsScreen(stage);
    }

    public void onLogoutButton(ActionEvent actionEvent) throws IOException {

        // TODO: Add confirm alert

        Stage stage = (Stage) logoutButton.getScene().getWindow();
        GlobalController.loginScreen(stage);
    }

    public void onSaveAppointmentButton(ActionEvent actionEvent) throws IOException, SQLException {

        // TODO: Complete save action after combo boxes
       AppointmentsDao.addAppointment(
                new Appointment(
                        AppointmentsDao.getIncrementedApptId(),
                        appointmentTitleNameTextField.getText(),
                        appointmentDescriptionTextField.getText(),
                        appointmentLocationTextField.getText(),
                        appointmentTypeTextField.getText(),
                        LocalDateTime.of(datePicker.getValue(),
                                startComboBox.getSelectionModel().getSelectedItem()),
                        LocalDateTime.of(datePicker.getValue(),
                                endComboBox.getSelectionModel().getSelectedItem()),
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
        GlobalController.viewAppointmentScreen(stage);
    }

    public void cancelAppointmentButton(ActionEvent actionEvent) throws IOException {

        // TODO: Add confirm alert

        Stage stage = (Stage) cancelAppointmentButton.getScene().getWindow();
        GlobalController.viewAppointmentScreen(stage);
    }
}
