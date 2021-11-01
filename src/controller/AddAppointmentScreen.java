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
import utils.alerts.Error;
import utils.alerts.Schedule;
import utils.auth.UserAuth;
import utils.time.Hours;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Optional;
import java.util.ResourceBundle;

/**
 * Class object to control the add appointment screen.
 * @author Andrew Cesar-Metzgus
 */
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

    /**
     * Static method to set what screen the customer previously came from, to return to that screen when
     * Appointment is added.
     * @param prevScreen str representation of the previous screen.
     */
    public static void setPreviousScreen(String prevScreen) { previousScreen = prevScreen; }

    /**
     * Static method to pass Customer data from another screen.
     * @param customer Customer object to pass.
     */
    public static void setCustFootball(Customer customer) { custFootball = customer; }

    /**
     * Initializer for the add appointment screen.
     * @param url url
     * @param resourceBundle resource bundle.
     */
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

    /**
     * Button click that moves to the view appointment screen.
     * Alerts if form has data confirming that the customer would like to discard
     * the data.
     * @param actionEvent Button click.
     * @throws IOException
     */
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


    /**
     * Button click that moves to the view customer screen.
     * Alerts if form has data confirming that the customer would like to discard
     * the data.
     * @param actionEvent Button click.
     * @throws IOException
     */
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


    /**
     * Button click that moves to the reports screen.
     * Alerts if form has data confirming that the customer would like to discard
     * the data.
     * @param actionEvent Button click.
     * @throws IOException
     */
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

    /**
     * Button click that moves to the login screen.
     * Alerts if form has data confirming that the customer would like to discard
     * the data.
     * @param actionEvent Button click.
     * @throws IOException
     */
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

    /**
     * Saves the form data to new appointment in program memory and database.
     * Logic checks: Appointment does not overlap another appointment and that
     * the appointment time is within business hours. Catches invalid or incomplete
     * form exception and alerts user.
     * @param actionEvent Button click.
     * @throws IOException
     */
    public void onSaveAppointmentButton(ActionEvent actionEvent) throws IOException, SQLException {
        try {
            validateFormComplete();

            LocalDateTime apptStart = LocalDateTime.of(datePicker.getValue(),
                    startComboBox.getSelectionModel().getSelectedItem());
            LocalDateTime apptEnd = LocalDateTime.of(datePicker.getValue(),
                    endComboBox.getSelectionModel().getSelectedItem());

            // Validate that new appointment does not overlap an existing appointment.
            if (!AppointmentsDao.openAppointmentTime(apptStart, apptEnd)) {
                Schedule.overlap();
            }
            // Validate that appointment is scheduled within biz hours.
            else if (!Hours.getStartTimes().contains(apptStart.toLocalTime()) &&
                    !Hours.getEndTimes().contains(apptEnd.toLocalTime())) {
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
        } catch (NullPointerException e) {
            Error.invalidForm();
        }
    }

    /**
     * Button click that cancels add appointment and moves to view appointment screen.
     * Alerts if form has data confirming that the user would like to discard
     * the data.
     * @param actionEvent Button click.
     * @throws IOException
     */
    public void cancelAppointmentButton(ActionEvent actionEvent) throws IOException {
        Stage stage = (Stage) cancelAppointmentButton.getScene().getWindow();
        if (noUserInput()) {
            if (previousScreen != null && previousScreen.equals("ViewCustomerScreen"))
                GlobalController.viewCustomerScreen(stage);
            else
                GlobalController.viewAppointmentScreen(stage);
        }
        else {
            Optional<ButtonType> userResponse = Confirm.cancel();
            if (userResponse.isPresent() && userResponse.get() == ButtonType.OK) {
                if (previousScreen != null && previousScreen.equals("ViewCustomerScreen"))
                    GlobalController.viewCustomerScreen(stage);
                else
                    GlobalController.viewAppointmentScreen(stage);
            }
        }

        previousScreen = null;
        custFootball = null;
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
     * Creates an array of the form field lists.
     * @return ArrayList of the form text fields.
     */
    public ArrayList<TextField> getTextFieldList() {
        ArrayList<TextField> textFieldList = new ArrayList<>();
        textFieldList.add(appointmentTitleNameTextField);
        textFieldList.add(appointmentDescriptionTextField);
        textFieldList.add(appointmentLocationTextField);
        textFieldList.add(appointmentTypeTextField);

        return textFieldList;
    }

    /**
     * Iterates through text fields to check if any have input.
     * @return Boolean if all boxes are empty.
     */
    public boolean noUserInput() {
        for (TextField tf: getTextFieldList()){
            if (!tf.getText().equals(""))
                return false;
        }
        return true;
    }

    /**
     * Iterates through text fields to check if any text fields
     * are empty. Throws a NullPointerException.
     */
    public void validateFormComplete(){

        for (TextField tf: getTextFieldList()){
            if (tf.getText().equals(""))
                throw new NullPointerException("Empty text field.");
        }
    }
}
