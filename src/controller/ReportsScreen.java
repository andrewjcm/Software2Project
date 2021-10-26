package controller;

import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableView;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class ReportsScreen implements Initializable {
    public Button appointmentsButton;
    public Button customersButton;
    public Button reportsButton;
    public Button logoutButton;
    public Button reportOneButton;
    public Button reportTwoButton;
    public Button reportThreeButton;
    public TableView reportsTable;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    public void onAppointmentsButton(ActionEvent actionEvent) throws IOException {
        Stage stage = (Stage) appointmentsButton.getScene().getWindow();
        GlobalController.viewAppointmentScreen(stage);
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

    public void onReportOneButton(ActionEvent actionEvent) {
    }

    public void onReportTwoButton(ActionEvent actionEvent) {
    }

    public void onReportThreeButton(ActionEvent actionEvent) {
    }
}
