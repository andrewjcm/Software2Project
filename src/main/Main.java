package main;

import controller.GlobalController;
import dao.AppointmentsDao;
import dao.CountriesDao;
import dao.DBConnection;
import javafx.application.Application;
import javafx.collections.ObservableList;
import javafx.stage.Stage;
import model.Appointment;
import model.Country;


/**
 * Main class of the program.
 * @author Andrew Cesar-Metzgus
 */
public class Main extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception {
        GlobalController.loginScreen(primaryStage);
    }

    /**
     * Main method that drives program.
     * @param args None.
     */
    public static void main(String[] args) {
        DBConnection.startConnection();
        launch(args);
        DBConnection.closeConnection();
    }
}