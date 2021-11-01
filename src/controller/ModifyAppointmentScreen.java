package controller;

import dao.AppointmentsDao;
import dao.ContactsDao;
import dao.CustomersDao;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.stage.Stage;
import model.Appointment;
import model.Contact;
import model.Customer;
import utils.time.Hours;
import utils.alerts.Confirm;
import utils.alerts.Schedule;
import utils.alerts.Error;
import utils.auth.UserAuth;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Optional;
import java.util.ResourceBundle;

/**
 * Controller object for modify appointment screen.
 * @author Andrew Cesar-Metzgus
 */
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

    /**
     * Initializes the modify appointment screen.
     * @param url url
     * @param resourceBundle Resource Bundle.
     */
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

    /**
     * Button click that moves to the view appointment screen.
     * Alerts if data has been modified confirming that the customer would like to discard
     * the data.
     * @param actionEvent Button click.
     * @throws IOException
     */
    public void onAppointmentsButton(ActionEvent actionEvent) throws IOException {
        Stage stage = (Stage) appointmentsButton.getScene().getWindow();
        if (appointmentToMod.equals(newAppointment())){
            GlobalController.viewAppointmentScreen(stage);
        }
        else {
            Optional<ButtonType> userResponse = Confirm.cancelMod();
            if (userResponse.isPresent() && userResponse.get() == ButtonType.OK) {
                GlobalController.viewAppointmentScreen(stage);
            }
        }
    }

    /**
     * Button click that moves to the view customer screen.
     * Alerts if data has been modified confirming that the customer would like to discard
     * the data.
     * @param actionEvent Button click.
     * @throws IOException
     */
    public void onCustomersButton(ActionEvent actionEvent) throws IOException {
        Stage stage = (Stage) customersButton.getScene().getWindow();
        if (appointmentToMod.equals(newAppointment())) {
            GlobalController.viewCustomerScreen(stage);
        }
        else {
            Optional<ButtonType> userResponse = Confirm.cancelMod();
            if (userResponse.isPresent() && userResponse.get() == ButtonType.OK) {
                GlobalController.viewCustomerScreen(stage);
            }
        }
    }

    /**
     * Button click that moves to the reports screen.
     * Alerts if data has been modified confirming that the customer would like to discard
     * the data.
     * @param actionEvent Button click.
     * @throws IOException
     */
    public void onReportsButton(ActionEvent actionEvent) throws IOException {
        Stage stage = (Stage) reportsButton.getScene().getWindow();
        if (appointmentToMod.equals(newAppointment())) {
            GlobalController.reportsScreen(stage);
        }
        else {
            Optional<ButtonType> userResponse = Confirm.cancelMod();
            if (userResponse.isPresent() && userResponse.get() == ButtonType.OK) {
                GlobalController.reportsScreen(stage);
            }
        }
    }

    /**
     * Button click that moves to the login screen.
     * Alerts if data has been modified confirming that the customer would like to discard
     * the data.
     * @param actionEvent Button click.
     * @throws IOException
     */
    public void onLogoutButton(ActionEvent actionEvent) throws IOException {
        Stage stage = (Stage) logoutButton.getScene().getWindow();
        if (appointmentToMod.equals(newAppointment())) {
            GlobalController.loginScreen(stage);
        }
        else {
            Optional<ButtonType> userResponse = Confirm.cancelMod();
            if (userResponse.isPresent() && userResponse.get() == ButtonType.OK) {
                GlobalController.loginScreen(stage);
            }
        }
    }

    /**
     * Updates the form data to new appointment in program memory and database.
     * Logic checks: Appointment does not overlap another appointment and that
     * the appointment time is within business hours. Catches invalid or incomplete
     * form exception and alerts user.
     * @param actionEvent Button click.
     * @throws IOException
     */
    public void onSaveAppointmentButton(ActionEvent actionEvent) throws IOException, SQLException {
        Appointment modAppt = newAppointment();

        if (appointmentToMod.equals(modAppt)){
            Error.noChangesMade();
        }
        else {
            LocalDateTime apptStart = LocalDateTime.of(datePicker.getValue(),
                    startComboBox.getSelectionModel().getSelectedItem());
            LocalDateTime apptEnd = LocalDateTime.of(datePicker.getValue(),
                    endComboBox.getSelectionModel().getSelectedItem());

            // Validate that new appointment does not overlap an existing appointment.
            if (!AppointmentsDao.openAppointmentTime(appointmentToMod, apptStart, apptEnd)){
                Schedule.overlap();
            }
            // Validate that appointment is scheduled within biz hours.
            else if (!Hours.getStartTimes().contains(apptStart.toLocalTime()) &&
                    !Hours.getEndTimes().contains(apptEnd.toLocalTime())){
                Schedule.outsideBizHours();
            }
            else {
                AppointmentsDao.updateAppointment(appointmentToModIndex, appointmentToMod, modAppt);
                Stage stage = (Stage) saveAppointmentButton.getScene().getWindow();
                GlobalController.viewAppointmentScreen(stage);
            }
        }
    }

    /**
     * Button click that cancels modify appointment and moves to view appointment screen.
     * Alerts if data has been modified confirming that the user would like to discard
     * the data.
     * @param actionEvent Button click.
     * @throws IOException
     */
    public void cancelAppointmentButton(ActionEvent actionEvent) throws IOException {
        Stage stage = (Stage) cancelAppointmentButton.getScene().getWindow();
        if (appointmentToMod.equals(newAppointment())) {
            GlobalController.viewAppointmentScreen(stage);
        }
        else {
            Optional<ButtonType> userResponse = Confirm.cancelMod();
            if (userResponse.isPresent() && userResponse.get() == ButtonType.OK) {
                GlobalController.viewAppointmentScreen(stage);
            }
        }
    }

    /**
     * On start time combo box new selection, a new list of times are generated
     * for the end time combo box. This prevents a user from selecting an end time
     * after the selected start time.
     * @param actionEvent Combobox selection
     */
    public void onStartCombo(ActionEvent actionEvent) {
        LocalTime startTime = startComboBox.getSelectionModel().getSelectedItem();
        if (startTime != null)
            endComboBox.setItems(Hours.getEndTimes(startTime));
        else
            endComboBox.setItems(Hours.getEndTimes());
    }

    /**
     * Creates a new appointment object.
     * @return Appointment object.
     */
    public Appointment newAppointment() {
        return new Appointment(
                appointmentToMod.getId(),
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
    }
}
