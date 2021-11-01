package utils.time;

import java.sql.Timestamp;
import java.time.*;

public class ZoneLocalize {

    public static final ZoneId dbZoneId = ZoneId.of("UTC");
    public static ZoneId sysZoneId = ZoneId.systemDefault();

    /**
     * Converts database Timestamp from UTC to local system default as LocalDateTime object.
     * @param timestamp Timestamp to be converted.
     * @return LocalDateTime
     */
    public static LocalDateTime toSysDefault(Timestamp timestamp) {
        ZonedDateTime zonedTime = timestamp.toLocalDateTime().atZone(dbZoneId);
        ZonedDateTime sysTimeZone = zonedTime.withZoneSameInstant(sysZoneId);
        return sysTimeZone.toLocalDateTime();
    }

    /**
     * Converts system default LocalDateTime to Timestamp object in UTC.
     * @param localDateTime LocalDateTime obj to be converted.
     * @return Timestamp
     */
    public static Timestamp toDb(LocalDateTime localDateTime) {
        ZonedDateTime zonedTime = localDateTime.atZone(sysZoneId);
        ZonedDateTime dbTimeZone = zonedTime.withZoneSameInstant(dbZoneId);
        return Timestamp.valueOf(dbTimeZone.toLocalDateTime());
    }

    public static LocalDateTime hoursFromEST(LocalTime hour) {
        ZonedDateTime zonedTime = LocalDateTime.of(LocalDate.now(), hour).atZone(ZoneId.of("America/New_York"));
        ZonedDateTime sysTimeZone = zonedTime.withZoneSameInstant(sysZoneId);
        return sysTimeZone.toLocalDateTime();
    }


}
