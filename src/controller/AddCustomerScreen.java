package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import utils.GlobalLocale;

import java.io.IOException;
import java.net.URL;
import java.util.Locale;
import java.util.ResourceBundle;

public class AddCustomerScreen implements Initializable {
    public Button appointmentsButton;
    public Button customersButton;
    public Button reportsButton;
    public Button logoutButton;
    public Button saveCustomerButton;
    public Button cancelCustomerButton;
    public TextField customerIdTextField;
    public TextField customerNameTextField;
    public TextField customerAddressTextField;
    public TextField customerPhoneTextField;
    public TextField customerZipTextField;
    public TextField customerCreateDateTextField;
    public TextField customerCreatedByTextField;
    public TextField customerLastUpdateTextField;
    public TextField customerUpdatedByTextField;
    public TextField customerDivisionTextField;

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

    public void onSaveCustomerButton(ActionEvent actionEvent) throws IOException {
        // TODO: Add confirm alert & save action
        Stage stage = (Stage) cancelCustomerButton.getScene().getWindow();
        GlobalController.viewCustomerScreen(stage);
    }

    public void cancelCustomerButton(ActionEvent actionEvent) throws IOException {

        // TODO: Add confirm alert
        Stage stage = (Stage) cancelCustomerButton.getScene().getWindow();
        GlobalController.viewCustomerScreen(stage);

    }
}
