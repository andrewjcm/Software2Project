package model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.time.LocalTime;
import java.time.temporal.ChronoUnit;

public class Hours {

    private static final LocalTime openHour = LocalTime.of(06,00,00);
    private static final LocalTime closeHour = LocalTime.of(17, 00, 00);
    private static ObservableList<LocalTime> startTimes = FXCollections.observableArrayList();
    private static ObservableList<LocalTime> endTimes = FXCollections.observableArrayList();

    public static ObservableList<LocalTime> getStartTimes() {
        int openHours = (int) ChronoUnit.HOURS.between(openHour, closeHour);

        for (int i = 0; i < openHours; i++)
            startTimes.add(openHour.plusHours(i));

        return startTimes;
    }

    public static ObservableList<LocalTime> getEndTimes() {
        int openHours = (int) ChronoUnit.HOURS.between(openHour, closeHour);

        for (int i = 0; i < (int)openHours; i++)
            endTimes.add(openHour.plusHours(i + 1));

        return endTimes;
    }
}
