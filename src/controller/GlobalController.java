package controller;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import model.Customer;

import java.io.IOException;
import java.util.Locale;
import java.util.ResourceBundle;

public class GlobalController {
    private static final Locale locale = Locale.CANADA_FRENCH;
    //private static final Locale locale = Locale.getDefault();
    private static final ResourceBundle resourceBundle = ResourceBundle.getBundle("utils.Lang", locale);

    public static void viewAppointmentScreen(Stage stage) throws IOException {
        Parent root = FXMLLoader.load(GlobalController.class.getResource("/view/ViewAppointmentsScreen.fxml"), resourceBundle);
        stage.setTitle(resourceBundle.getString("App_Title"));
        stage.setScene(new Scene(root, 1000, 600));
        stage.show();
    }

    public static void viewCustomerScreen(Stage stage) throws IOException {
        Parent root = FXMLLoader.load(GlobalController.class.getResource("/view/ViewCustomerScreen.fxml"), resourceBundle);
        stage.setTitle(resourceBundle.getString("App_Title"));
        stage.setScene(new Scene(root, 1000, 600));
        stage.show();
    }

    public static void addAppointmentScreen(Stage stage) throws IOException {
        Parent root = FXMLLoader.load(GlobalController.class.getResource("/view/AddAppointmentScreen.fxml"), resourceBundle);
        stage.setTitle(resourceBundle.getString("App_Title"));
        stage.setScene(new Scene(root, 1000, 600));
        stage.show();
    }

    // TODO: Add selected Customer
/**
    public static void addAppointmentScreen(Stage stage, Customer selectedCustomer) throws IOException {
        Parent root = FXMLLoader.load(GlobalController.class.getResource("/view/AddAppointmentScreen.fxml"), resourceBundle);
        stage.setTitle(resourceBundle.getString("App_Title"));
        stage.setScene(new Scene(root, 1000, 600));
        stage.show();
    }
 */

    public static void addCustomerScreen(Stage stage) throws IOException {
        Parent root = FXMLLoader.load(GlobalController.class.getResource("/view/AddCustomerScreen.fxml"), resourceBundle);
        stage.setTitle(resourceBundle.getString("App_Title"));
        stage.setScene(new Scene(root, 1000, 600));
        stage.show();
    }

    public static void modifyAppointmentScreen(Stage stage) throws IOException {
        // TODO: Need to add selected Appointment

        Parent root = FXMLLoader.load(GlobalController.class.getResource("/view/ModifyAppointmentScreen.fxml"), resourceBundle);
        stage.setTitle(resourceBundle.getString("App_Title"));
        stage.setScene(new Scene(root, 1000, 600));
        stage.show();
    }

    public static void modifyCustomerScreen(Stage stage) throws IOException {
        // TODO: Need to add selected Customer

        Parent root = FXMLLoader.load(GlobalController.class.getResource("/view/ModifyCustomerScreen.fxml"), resourceBundle);
        stage.setTitle(resourceBundle.getString("App_Title"));
        stage.setScene(new Scene(root, 1000, 600));
        stage.show();
    }

    public static void reportsScreen(Stage stage) throws IOException {
        Parent root = FXMLLoader.load(GlobalController.class.getResource("/view/ReportsScreen.fxml"), resourceBundle);
        stage.setTitle(resourceBundle.getString("App_Title"));
        stage.setScene(new Scene(root, 1000, 600));
        stage.show();

    }

    public static void loginScreen(Stage stage) throws IOException {
        Parent root = FXMLLoader.load(GlobalController.class.getResource("/view/LoginScreen.fxml"), resourceBundle);
        stage.setTitle(resourceBundle.getString("App_Title"));
        stage.setScene(new Scene(root, 1000, 600));
        stage.show();
    }
}
