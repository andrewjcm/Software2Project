package controller;

import dao.Query;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class ReportsScreen implements Initializable {
    public Button appointmentsButton;
    public Button customersButton;
    public Button reportsButton;
    public Button logoutButton;
    public TableView monthTable;
    public TableColumn monthCol;
    public TableColumn monthCountCol;
    public TableView typeTable;
    public TableColumn typeCol;
    public TableColumn typeCountCol;
    public TableView contactScheduleTable;
    public TableColumn nameCol;
    public TableColumn apptIdCol;
    public TableColumn titleCol;
    public TableColumn descCol;
    public TableColumn startCol;
    public TableColumn endCol;
    public TableColumn custIdCol;
    public TableView divisionTable;
    public TableColumn divCol;
    public TableColumn divCountCol;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        contactScheduleTable.setItems(Query.getAllContactSchedules());
        nameCol.setCellValueFactory(new PropertyValueFactory<>("contactName"));
        apptIdCol.setCellValueFactory(new PropertyValueFactory<>("appointmentId"));
        titleCol.setCellValueFactory(new PropertyValueFactory<>("title"));
        descCol.setCellValueFactory(new PropertyValueFactory<>("desc"));
        startCol.setCellValueFactory(new PropertyValueFactory<>("start"));
        endCol.setCellValueFactory(new PropertyValueFactory<>("end"));
        custIdCol.setCellValueFactory(new PropertyValueFactory<>("customerId"));

        monthTable.setItems(Query.getAllMonthCounts());
        monthCol.setCellValueFactory(new PropertyValueFactory<>("str"));
        monthCountCol.setCellValueFactory(new PropertyValueFactory<>("count"));

        typeTable.setItems(Query.getAllTypeCounts());
        typeCol.setCellValueFactory(new PropertyValueFactory<>("str"));
        typeCountCol.setCellValueFactory(new PropertyValueFactory<>("count"));

        divisionTable.setItems(Query.getAllDivisionCounts());
        divCol.setCellValueFactory(new PropertyValueFactory<>("str"));
        divCountCol.setCellValueFactory(new PropertyValueFactory<>("count"));

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
    }

    public void onLogoutButton(ActionEvent actionEvent) throws IOException {
        Stage stage = (Stage) logoutButton.getScene().getWindow();
        GlobalController.loginScreen(stage);
    }
}
