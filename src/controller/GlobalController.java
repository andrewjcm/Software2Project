package controller;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import utils.auth.UserAuth;

import java.io.IOException;
import java.util.Locale;
import java.util.ResourceBundle;

/**
 * Controls screen movement for entire program.
 * @author Andrew Cesar-Metzgus
 */
public class GlobalController {

    private static final Locale locale = Locale.getDefault();
    private static final ResourceBundle resourceBundle = ResourceBundle.getBundle("utils.Lang", locale);

    /**
     * Gets the current resource bundle.
     * @return Resource Bundle
     */
    public static ResourceBundle getResourceBundle() { return resourceBundle; }

    /**
     * Sets current stage to view appointment screen.
     * @param stage Current stage.
     * @throws IOException
     */
    public static void viewAppointmentScreen(Stage stage) throws IOException {
        Parent root = FXMLLoader.load(GlobalController.class.getResource("/view/ViewAppointmentsScreen.fxml"), resourceBundle);
        stage.setTitle(resourceBundle.getString("App_Title"));
        stage.setScene(new Scene(root, 1000, 600));
        stage.show();
    }

    /**
     * Sets current stage to view customer screen.
     * @param stage Current stage.
     * @throws IOException
     */
    public static void viewCustomerScreen(Stage stage) throws IOException {
        Parent root = FXMLLoader.load(GlobalController.class.getResource("/view/ViewCustomerScreen.fxml"), resourceBundle);
        stage.setTitle(resourceBundle.getString("App_Title"));
        stage.setScene(new Scene(root, 1000, 600));
        stage.show();
    }

    /**
     * Sets current stage to add appointment screen.
     * @param stage Current stage.
     * @throws IOException
     */
    public static void addAppointmentScreen(Stage stage) throws IOException {
        Parent root = FXMLLoader.load(GlobalController.class.getResource("/view/AddAppointmentScreen.fxml"), resourceBundle);
        stage.setTitle(resourceBundle.getString("App_Title"));
        stage.setScene(new Scene(root, 1000, 600));
        stage.show();
    }

    /**
     * Sets current stage to add customer screen.
     * @param stage Current stage.
     * @throws IOException
     */
    public static void addCustomerScreen(Stage stage) throws IOException {
        Parent root = FXMLLoader.load(GlobalController.class.getResource("/view/AddCustomerScreen.fxml"), resourceBundle);
        stage.setTitle(resourceBundle.getString("App_Title"));
        stage.setScene(new Scene(root, 1000, 600));
        stage.show();
    }

    /**
     * Sets current stage to modify appointment screen.
     * @param stage Current stage.
     * @throws IOException
     */
    public static void modifyAppointmentScreen(Stage stage) throws IOException {
        Parent root = FXMLLoader.load(GlobalController.class.getResource("/view/ModifyAppointmentScreen.fxml"), resourceBundle);
        stage.setTitle(resourceBundle.getString("App_Title"));
        stage.setScene(new Scene(root, 1000, 600));
        stage.show();
    }

    /**
     * Sets current stage to modify customer screen.
     * @param stage Current stage.
     * @throws IOException
     */
    public static void modifyCustomerScreen(Stage stage) throws IOException {
        Parent root = FXMLLoader.load(GlobalController.class.getResource("/view/ModifyCustomerScreen.fxml"), resourceBundle);
        stage.setTitle(resourceBundle.getString("App_Title"));
        stage.setScene(new Scene(root, 1000, 600));
        stage.show();
    }

    /**
     * Sets current stage to reports screen.
     * @param stage Current stage.
     * @throws IOException
     */
    public static void reportsScreen(Stage stage) throws IOException {
        Parent root = FXMLLoader.load(GlobalController.class.getResource("/view/ReportsScreen.fxml"), resourceBundle);
        stage.setTitle(resourceBundle.getString("App_Title"));
        stage.setScene(new Scene(root, 1000, 600));
        stage.show();

    }

    /**
     * Sets current stage to login screen.
     * @param stage Current stage.
     * @throws IOException
     */
    public static void loginScreen(Stage stage) throws IOException {
        Parent root = FXMLLoader.load(GlobalController.class.getResource("/view/LoginScreen.fxml"), resourceBundle);
        stage.setTitle(resourceBundle.getString("App_Title"));
        stage.setScene(new Scene(root, 1000, 600));
        stage.show();

        ViewAppointmentsScreen.setInitialLogin(true);
        UserAuth.setLoggedInUser(null);
    }
}
