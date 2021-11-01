package controller;

import dao.CountriesDao;
import dao.CustomersDao;
import dao.DivisionsDao;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import model.Country;
import model.Customer;
import model.Division;
import utils.alerts.Confirm;
import utils.auth.UserAuth;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Optional;
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
    public ComboBox<Country> countryCombo;
    public ComboBox<Division> divisionCombo;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        countryCombo.setItems(CountriesDao.getAllCountries());
        divisionCombo.setItems(DivisionsDao.getAllDivisions());

        customerCreateDateTextField.setText(LocalDateTime.now().toString());
        customerCreatedByTextField.setText(UserAuth.getLoggedInUser().toString());
        customerLastUpdateTextField.setText(LocalDateTime.now().toString());
        customerUpdatedByTextField.setText(UserAuth.getLoggedInUser().toString());

    }

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

    public void onSaveCustomerButton(ActionEvent actionEvent) throws IOException, SQLException {
        CustomersDao.addCustomer(
                new Customer(
                        CustomersDao.getIncrementedCustId(),
                        customerNameTextField.getText(),
                        customerAddressTextField.getText(),
                        customerZipTextField.getText(),
                        customerPhoneTextField.getText(),
                        LocalDateTime.now(),
                        UserAuth.getLoggedInUser().getUserName(),
                        LocalDateTime.now(),
                        UserAuth.getLoggedInUser().getUserName(),
                        divisionCombo.getSelectionModel().getSelectedItem()
                )
        );

        Stage stage = (Stage) cancelCustomerButton.getScene().getWindow();
        GlobalController.viewCustomerScreen(stage);
    }

    public void cancelCustomerButton(ActionEvent actionEvent) throws IOException {
        Stage stage = (Stage) cancelCustomerButton.getScene().getWindow();
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

    public void onCountryComboAction(ActionEvent actionEvent) {
        Country selectedCountry = countryCombo.getSelectionModel().getSelectedItem();
        if (selectedCountry != null)
            divisionCombo.setItems(DivisionsDao.getFilteredDivisions(selectedCountry));
        else
            divisionCombo.setItems(DivisionsDao.getAllDivisions());
    }

    public ArrayList<TextField> getTextFieldList() {
        ArrayList<TextField> textFieldList = new ArrayList<>();
        textFieldList.add(customerNameTextField);
        textFieldList.add(customerAddressTextField);
        textFieldList.add(customerZipTextField);
        textFieldList.add(customerPhoneTextField);

        return textFieldList;
    }

    public boolean noUserInput() {
        for (TextField tf: getTextFieldList()){
            if (!tf.getText().equals(""))
                return false;
        }
        return true;
    }
}
