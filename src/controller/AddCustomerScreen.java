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
import javafx.stage.Stage;
import model.Country;
import model.Customer;
import model.Division;
import utils.alerts.Confirm;
import utils.alerts.Error;
import utils.auth.UserAuth;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Optional;
import java.util.ResourceBundle;

/**
 * Class object to control the add customer screen.
 * @author Andrew Cesar-Metzgus
 */
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

    /**
     * Initializer for the add appointment screen.
     * @param url url
     * @param resourceBundle resource bundle.
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        countryCombo.setItems(CountriesDao.getAllCountries());
        divisionCombo.setItems(DivisionsDao.getAllDivisions());

        customerCreateDateTextField.setText(LocalDateTime.now().toString());
        customerCreatedByTextField.setText(UserAuth.getLoggedInUser().toString());
        customerLastUpdateTextField.setText(LocalDateTime.now().toString());
        customerUpdatedByTextField.setText(UserAuth.getLoggedInUser().toString());

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
     * Logic checks: . Catches invalid or incomplete form exception and alerts user.
     * @param actionEvent Button click.
     * @throws IOException
     */
    public void onSaveCustomerButton(ActionEvent actionEvent) throws IOException, SQLException {
        try {
            validateFormComplete();

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
        } catch (NullPointerException e){
            Error.invalidForm();
        }
    }

    /**
     * Button click that cancels add customer and moves to view customer screen.
     * Alerts if form has data confirming that the user would like to discard
     * the data.
     * @param actionEvent Button click.
     * @throws IOException
     */
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

    /**
     * On country selection, division combo box is filtered based on database
     * relation.
     * @param actionEvent ComboBox selected.
     */
    public void onCountryComboAction(ActionEvent actionEvent) {
        Country selectedCountry = countryCombo.getSelectionModel().getSelectedItem();
        if (selectedCountry != null)
            divisionCombo.setItems(DivisionsDao.getFilteredDivisions(selectedCountry));
        else
            divisionCombo.setItems(DivisionsDao.getAllDivisions());
    }

    /**
     * Creates an array of the form field lists.
     * @return ArrayList of the form text fields.
     */
    public ArrayList<TextField> getTextFieldList() {
        ArrayList<TextField> textFieldList = new ArrayList<>();
        textFieldList.add(customerNameTextField);
        textFieldList.add(customerAddressTextField);
        textFieldList.add(customerZipTextField);
        textFieldList.add(customerPhoneTextField);

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
