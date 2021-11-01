package controller;

import dao.AppointmentsDao;
import dao.CustomersDao;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;
import model.Appointment;
import model.Customer;
import utils.alerts.Confirm;
import utils.alerts.Error;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Optional;
import java.util.ResourceBundle;

public class ViewCustomerScreen implements Initializable {
    public Button appointmentsButton;
    public Button customersButton;
    public Button reportsButton;
    public Button logoutButton;
    public Button addCustomerButton;
    public Button modifyCustomerButton;
    public Button deleteCustomerButton;
    public Button addCustomerAppointmentButton;
    public TableView customersTable;
    public TableColumn customersIdCol;
    public TableColumn customersNameCol;
    public TableColumn customersAddressCol;
    public TableColumn customersZipCol;
    public TableColumn customersPhoneCol;
    public TableColumn customersDivisionCol;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        customersTable.setItems(CustomersDao.getAllCustomers());

        customersIdCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        customersNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        customersAddressCol.setCellValueFactory(new PropertyValueFactory<>("address"));
        customersZipCol.setCellValueFactory(new PropertyValueFactory<>("postalCode"));
        customersPhoneCol.setCellValueFactory(new PropertyValueFactory<>("phone"));
        customersDivisionCol.setCellValueFactory(new PropertyValueFactory<>("division"));

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
        Customer selectedCust = (Customer) customersTable.getSelectionModel().getSelectedItem();
        if (selectedCust == null) {
            Error.noItemSelected();
        }
        else {
            ModifyCustomerScreen.setCustToMod(selectedCust);
            Stage stage = (Stage) modifyCustomerButton.getScene().getWindow();
            GlobalController.modifyCustomerScreen(stage);
        }
    }

    public void onDeleteCustomerButton(ActionEvent actionEvent) throws SQLException {
        Customer selectedCust = (Customer) customersTable.getSelectionModel().getSelectedItem();
        if (selectedCust == null)
            Error.noItemSelected();
        else {
            Optional<ButtonType> userResponse = Confirm.delete();
            if (userResponse.isPresent() && userResponse.get() == ButtonType.OK) {
                ArrayList<Appointment> apptToDelete = new ArrayList<>();

                for (Appointment appt : AppointmentsDao.getAllAppointments()) {
                    if (selectedCust.equals(appt.getCustomer())) {
                        apptToDelete.add(appt);
                    }
                }

                if (apptToDelete.isEmpty()) {
                    CustomersDao.deleteCustomer(selectedCust);
                } else {
                    Optional<ButtonType> userResponse2 = Confirm.deleteAssociatedAppts(apptToDelete.size());
                    if (userResponse2.isPresent() && userResponse2.get() == ButtonType.OK) {
                        for (Appointment deleteAppt : apptToDelete){
                            AppointmentsDao.deleteAppointment(deleteAppt);
                        }
                        CustomersDao.deleteCustomer(selectedCust);
                    }
                }
            }
        }

    }

    public void onAddCustomerAppointmentButton(ActionEvent actionEvent) throws IOException {
        Customer selectedCust = (Customer) customersTable.getSelectionModel().getSelectedItem();

        AddAppointmentScreen.setCustFootball(selectedCust);
        AddAppointmentScreen.setPreviousScreen("ViewCustomerScreen");

        Stage stage = (Stage) addCustomerAppointmentButton.getScene().getWindow();
        GlobalController.addAppointmentScreen(stage);
    }
}
