package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;
import utils.GlobalLocale;

import java.io.IOException;
import java.net.URL;
import java.util.Locale;
import java.util.ResourceBundle;

public class ViewCustomerScreen implements Initializable {
    public Button appointmentsButton;
    public Button customersButton;
    public Button reportsButton;
    public Button logoutButton;
    public Button addCustomerButton;
    public Button modifyCustomerButton;
    public Button deleteCustomerButton;
    public TextField customerSearchTextField;
    public Button addCustomerAppointmentButton;
    public TableView customersTable;
    public TableColumn customersIdCol;
    public TableColumn customersNameCol;
    public TableColumn customersAddressCol;
    public TableColumn customersZipCol;
    public TableColumn customersPhoneCol;

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

    public void onAddCustomerButton(ActionEvent actionEvent) throws IOException {
        Stage stage = (Stage) addCustomerButton.getScene().getWindow();
        GlobalController.addCustomerScreen(stage);
    }

    public void onModifyCustomerButton(ActionEvent actionEvent) throws IOException {
        // TODO: Add selected Customer

        Stage stage = (Stage) modifyCustomerButton.getScene().getWindow();
        GlobalController.modifyCustomerScreen(stage);
    }

    public void onDeleteCustomerButton(ActionEvent actionEvent) {
    }

    public void onSearchCustomerKeyTyped(KeyEvent keyEvent) {
    }

    public void onAddCustomerAppointmentButton(ActionEvent actionEvent) {
    }
}
