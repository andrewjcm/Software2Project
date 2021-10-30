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
import javafx.stage.Stage;
import model.Appointment;
import model.Contact;
import model.Customer;
import model.Hours;
import utils.auth.UserAuth;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Locale;
import java.util.ResourceBundle;

public class ModifyAppointmentScreen implements Initializable {
    public Button appointmentsButton;
    public Button customersButton;
    public Button reportsButton;
    public Button logoutButton;
    public Button saveAppointmentButton;
    public Button cancelAppointmentButton;
    public TextField appointmentIdTextField;
    public TextField appointmentDescriptionTextField;
    public TextField appointmentLocationTextField;
    public TextField appointmentTypeTextField;
    public TextField appointmentCreateDateTextField;
    public TextField appointmentCreatedByTextField;
    public TextField appointmentLastUpdateTextField;
    public TextField appointmentUpdatedByTextField;
    public TextField appointmentTitleNameTextField;
    public ComboBox<LocalTime> startComboBox;
    public ComboBox<LocalTime> endComboBox;
    public ComboBox<Customer> customerComboBox;
    public DatePicker datePicker;
    public ComboBox<Contact> contactComboBox;

    public static Appointment appointmentToMod;
    public static int appointmentToModIndex;

    /**
     * Static method to pass data from the view screen to the modify screen.
     * @param appt The Appointment to modify.
     */
    public static void setAppointmentToMod(Appointment appt){
        appointmentToMod = appt;
        appointmentToModIndex = AppointmentsDao.getAllAppointments().indexOf(appt);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        startComboBox.setItems(Hours.getStartTimes());
        endComboBox.setItems(Hours.getEndTimes());
        customerComboBox.setItems(CustomersDao.getAllCustomers());
        contactComboBox.setItems(ContactsDao.getAllContacts());

        Appointment appt = appointmentToMod;
        appointmentIdTextField.setText(String.valueOf(appt.getId()));
        appointmentTitleNameTextField.setText(appt.getTitle());
        appointmentDescriptionTextField.setText(appt.getDescription());
        appointmentLocationTextField.setText(appt.getLocation());
        appointmentTypeTextField.setText(appt.getType());
        datePicker.setValue(appt.getStart().toLocalDate());
        startComboBox.setValue(appt.getStart().toLocalTime());
        endComboBox.setValue(appt.getEnd().toLocalTime());
        customerComboBox.setValue(appt.getCustomer());
        contactComboBox.setValue(appt.getContact());
        appointmentCreateDateTextField.setText(appt.getCreateDate().toString());
        appointmentCreatedByTextField.setText(appt.getCreatedBy());
        appointmentLastUpdateTextField.setText(LocalDateTime.now().toString());
        appointmentUpdatedByTextField.setText(UserAuth.getLoggedInUser().toString());
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
        Appointment modAppt = new Appointment(
                AppointmentsDao.getIncrementedApptId(),
                appointmentTitleNameTextField.getText(),
                appointmentDescriptionTextField.getText(),
                appointmentLocationTextField.getText(),
                appointmentTypeTextField.getText(),
                LocalDateTime.of(datePicker.getValue(),
                        startComboBox.getSelectionModel().getSelectedItem()),
                LocalDateTime.of(datePicker.getValue(),
                        endComboBox.getSelectionModel().getSelectedItem()),
                appointmentToMod.getCreateDate(),
                appointmentToMod.getCreatedBy(),
                LocalDateTime.now(),
                UserAuth.getLoggedInUser().toString(),
                customerComboBox.getSelectionModel().getSelectedItem(),
                appointmentToMod.getUser(),
                contactComboBox.getSelectionModel().getSelectedItem()
        );

        if (appointmentToMod.equals(modAppt)){
            // TODO: Error nothing changed.
        }
        else {

            AppointmentsDao.updateAppointment(appointmentToModIndex, appointmentToMod, modAppt);
            Stage stage = (Stage) saveAppointmentButton.getScene().getWindow();
            GlobalController.viewAppointmentScreen(stage);
        }
    }

    public void cancelAppointmentButton(ActionEvent actionEvent) throws IOException {

        // TODO: Add confirm alert

        Stage stage = (Stage) cancelAppointmentButton.getScene().getWindow();
        GlobalController.viewAppointmentScreen(stage);
    }
}
