package main;

import controller.GlobalController;
import javafx.application.Application;
import javafx.stage.Stage;


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
        launch(args);
    }
}