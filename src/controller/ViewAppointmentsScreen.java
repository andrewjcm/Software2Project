package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import utils.GlobalLocale;

import java.io.IOException;
import java.net.URL;
import java.util.Locale;
import java.util.ResourceBundle;

public class ViewAppointmentsScreen implements Initializable {
    public Button appointmentsButton;
    public Button customersButton;
    public Button reportsButton;
    public Button logoutButton;
    public AnchorPane contentPane;
    public Button addAppointmentButton;
    public Button modifyAppointmentButton;
    public Button deleteAppointmentButton;
    public RadioButton viewWeekRadioButton;
    public RadioButton viewMonthRadioButton;
    public RadioButton viewAllRadioButton;
    public ToggleGroup tGroup;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
    }

    public void onAppointmentsButton(ActionEvent actionEvent) {
        // Pass already on this screen.
    }

    public void onCustomersButton(ActionEvent actionEvent) throws IOException {
        Stage stage = (Stage) customersButton.getScene().getWindow();
        GlobalController.viewCustomerScreen(stage);
    }

    public void onReportsButton(ActionEvent actionEvent) throws IOException {
        Stage stage = (Stage) reportsButton.getScene().getWindow();
        GlobalController.reportsScreen(stage);
    }

    public void onLogoutButton(ActionEvent actionEvent) throws IOException {
        Stage stage = (Stage) logoutButton.getScene().getWindow();
        GlobalController.loginScreen(stage);
    }

    public void onAddAppointmentButton(ActionEvent actionEvent) throws IOException {
        Stage stage = (Stage) addAppointmentButton.getScene().getWindow();
        GlobalController.addAppointmentScreen(stage);
    }

    public void onModifyAppointmentButton(ActionEvent actionEvent) throws IOException {
        Stage stage = (Stage) modifyAppointmentButton.getScene().getWindow();
        GlobalController.modifyAppointmentScreen(stage);
    }

    public void onDeleteAppointmentButton(ActionEvent actionEvent) {
    }

    public void onViewWeekRadioButton(ActionEvent actionEvent) {
    }

    public void OnViewMonthRadioButton(ActionEvent actionEvent) {
    }

    public void onViewAllRadioButton(ActionEvent actionEvent) {
    }
}
