package controller;

import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class ModifyAppointmentScreen implements Initializable {
    public Button appointmentsButton;
    public Button customersButton;
    public Button reportsButton;
    public Button logoutButton;
    public Button saveAppointmentButton;
    public Button cancelAppointmentButton;
    public TextField appointmentIdTextField;
    public TextField appointmentCustomerTextField;
    public TextField appointmentDescriptionTextField;
    public TextField appointmentLocationTextField;
    public TextField appointmentTypeTextField;
    public TextField appointmentDateTextField;
    public TextField appointmentStartByTextField;
    public TextField appointmentEndTextField;
    public TextField appointmentContactNameTextField;
    public TextField appointmentContactEmailTextField;
    public TextField appointmentCreateDateTextField;
    public TextField appointmentCreatedByTextField;
    public TextField appointmentLastUpdateTextField;
    public TextField appointmentUpdatedByTextField;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

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

    public void onSaveAppointmentButton(ActionEvent actionEvent) throws IOException {

        // TODO: Add save action

        Stage stage = (Stage) saveAppointmentButton.getScene().getWindow();
        GlobalController.viewAppointmentScreen(stage);
    }

    public void cancelAppointmentButton(ActionEvent actionEvent) throws IOException {

        // TODO: Add confirm alert

        Stage stage = (Stage) cancelAppointmentButton.getScene().getWindow();
        GlobalController.viewAppointmentScreen(stage);
    }
}
