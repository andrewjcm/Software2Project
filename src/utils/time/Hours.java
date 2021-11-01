package utils.time;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import utils.time.ZoneLocalize;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;

/**
 * Business and appointment hours object.
 * @author Andrew Cesar-Metzgus
 */
public class Hours {

    private static final LocalTime openHourEst = LocalTime.of(8,0,0); // opening hour
    private static final LocalTime closeHourEst = LocalTime.of(22, 0, 0); // 1 hour before closing.
    private static final LocalDateTime openHour = ZoneLocalize.hoursFromEST(openHourEst);
    private static final LocalDateTime closeHour = ZoneLocalize.hoursFromEST(closeHourEst);
    private static ObservableList<LocalTime> startTimes = FXCollections.observableArrayList();
    private static ObservableList<LocalTime> endTimes = FXCollections.observableArrayList();

    /**
     * Creates a list of appointment start times.
     * @return ObservableList of LocalTime.
     */
    public static ObservableList<LocalTime> getStartTimes() {
        int openHours = (int) ChronoUnit.HOURS.between(openHour, closeHour);

        for (int i = 0; i < openHours; i++)
            startTimes.add(openHour.plusHours(i).toLocalTime());

        return startTimes;
    }

    /**
     * Creates a list of appointment end times.
     * @return ObservableList of LocalTime.
     */
    public static ObservableList<LocalTime> getEndTimes() {
        if (endTimes.size() > 0)
            endTimes.clear();
        int openHours = (int) ChronoUnit.HOURS.between(openHour, closeHour);

        for (int i = 0; i < openHours; i++)
            endTimes.add(openHour.plusHours(i + 1).toLocalTime());

        return endTimes;
    }

    /**
     * Creates a list of appointment end times based on input start time.
     * @param startTime LocalTime start time.
     * @return ObservableList of LocalTime.
     */
    public static ObservableList<LocalTime> getEndTimes(LocalTime startTime) {
        if (endTimes.size() > 0)
            endTimes.clear();
        LocalDateTime dateTime = LocalDateTime.of(openHour.toLocalDate(), startTime);
        int openHours = (int) ChronoUnit.HOURS.between(dateTime, closeHour);
        if (openHours > 14) {
            dateTime = LocalDateTime.of(closeHour.toLocalDate(), startTime);
            openHours = (int) ChronoUnit.HOURS.between(dateTime, closeHour);
        }

        for (int i = 0; i < openHours; i++)
            endTimes.add(dateTime.plusHours(i + 1).toLocalTime());

        return endTimes;
    }
}
