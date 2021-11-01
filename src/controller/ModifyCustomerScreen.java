package controller;

import dao.AppointmentsDao;
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
import model.Appointment;
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
import java.util.Optional;
import java.util.ResourceBundle;

/**
 * Controller object for modify customer screen.
 * @author Andrew Cesar-Metzgus
 */
public class ModifyCustomerScreen implements Initializable {
    public Button appointmentsButton;
    public Button customersButton;
    public Button reportsButton;
    public Button logoutButton;
    public Button saveCustomerButton;
    public Button cancelCustomerButton;
    public TextField customerIdTextField;
    public TextField customerNameTextField;
    public TextField customerAddressTextField;
    public TextField customerZipTextField;
    public TextField customerPhoneTextField;
    public TextField customerCreateDateTextField;
    public TextField customerCreatedByTextField;
    public TextField customerLastUpdateTextField;
    public TextField customerUpdatedByTextField;
    public ComboBox<Country> countryCombo;
    public ComboBox<Division> divisionCombo;

    public static Customer custToMod;
    public static int custToModIndex;

    /**
     * Static method to pass data from the view customer screen to the modify screen.
     * @param cust The Customer to modify.
     */
    public static void setCustToMod(Customer cust){
        custToMod = cust;
        custToModIndex = CustomersDao.getAllCustomers().indexOf(cust);
    }

    /**
     * Initializes the modify customer screen.
     * @param url url
     * @param resourceBundle Resource Bundle.
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        countryCombo.setItems(CountriesDao.getAllCountries());
        divisionCombo.setItems(DivisionsDao.getAllDivisions());

        customerIdTextField.setText(String.valueOf(custToMod.getId()));
        customerNameTextField.setText(custToMod.getName());
        customerAddressTextField.setText(custToMod.getAddress());
        customerZipTextField.setText(custToMod.getPostalCode());
        customerPhoneTextField.setText(custToMod.getPhone());
        countryCombo.setValue(custToMod.getDivision().getCountry());
        divisionCombo.setValue(custToMod.getDivision());
        customerCreateDateTextField.setText(custToMod.getCreateDate().toString());
        customerCreatedByTextField.setText(custToMod.getCreatedBy());
        customerLastUpdateTextField.setText(LocalDateTime.now().toString());
        customerUpdatedByTextField.setText(UserAuth.getLoggedInUser().toString());


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
        if (custToMod.equals(newCustomer())) {
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
        if (custToMod.equals(newCustomer())) {
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
        if (custToMod.equals(newCustomer())) {
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
        if (custToMod.equals(newCustomer())) {
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
     * Updates the form data to new customer in program memory and database.
     * Logic checks: Form data has been modified.
     * @param actionEvent
     * @throws IOException
     * @throws SQLException
     */
    public void onSaveCustomerButton(ActionEvent actionEvent) throws IOException, SQLException {
        Customer modCust = newCustomer();
        if (!custToMod.equals(modCust)) {
            CustomersDao.updateCustomer(custToModIndex, custToMod, modCust);
            for (Appointment appt : AppointmentsDao.getAllAppointments()){
                if (appt.getCustomer().equals(custToMod))
                    appt.setCustomer(modCust);
            }
            Stage stage = (Stage) cancelCustomerButton.getScene().getWindow();
            GlobalController.viewCustomerScreen(stage);
        }
        else {
            Error.noChangesMade();
        }
    }

    /**
     * Button click that cancels modify customer and moves to view appointment screen.
     * Alerts if data has been modified confirming that the user would like to discard
     * the data.
     * @param actionEvent Button click.
     * @throws IOException
     */
    public void cancelCustomerButton(ActionEvent actionEvent) throws IOException {
        Stage stage = (Stage) cancelCustomerButton.getScene().getWindow();
        if (custToMod.equals(newCustomer())) {
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
     * On country selection, division combo box is filtered based on database
     * relation.
     * @param actionEvent ComboBox selected.
     */
    public void onCountryCombo(ActionEvent actionEvent) {
        Country selectedCountry = countryCombo.getSelectionModel().getSelectedItem();
        if (selectedCountry != null)
            divisionCombo.setItems(DivisionsDao.getFilteredDivisions(selectedCountry));
        else
            divisionCombo.setItems(DivisionsDao.getAllDivisions());
    }

    /**
     * Creates a new customer object.
     * @return Customer object.
     */
    public Customer newCustomer(){
        return new Customer(
                CustomersDao.getIncrementedCustId(),
                customerNameTextField.getText(),
                customerAddressTextField.getText(),
                customerZipTextField.getText(),
                customerPhoneTextField.getText(),
                custToMod.getCreateDate(),
                custToMod.getCreatedBy(),
                LocalDateTime.now(),
                UserAuth.getLoggedInUser().toString(),
                divisionCombo.getSelectionModel().getSelectedItem()
        );
    }
}
