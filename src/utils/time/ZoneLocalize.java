package utils.time;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;

public class ZoneLocalize {

    public static final ZoneId dbZoneId = ZoneId.of("UTC");
    public static ZoneId sysZoneId = ZoneId.systemDefault();

    /**
     * Converts database Timestamp from UTC to local system default as LocalDateTime object.
     * @param timestamp
     * @return LocalDateTime
     */
    public static LocalDateTime toSysDefault(Timestamp timestamp) {
        ZonedDateTime zonedTime = timestamp.toLocalDateTime().atZone(dbZoneId);
        ZonedDateTime sysTimeZone = zonedTime.withZoneSameInstant(sysZoneId);
        return sysTimeZone.toLocalDateTime();
    }

    /**
     * Converts system default LocalDateTime to Timestamp object in UTC.
     * @param localDateTime
     * @return Timestamp
     */
    public static Timestamp toDb(LocalDateTime localDateTime) {
        ZonedDateTime zonedTime = localDateTime.atZone(sysZoneId);
        ZonedDateTime dbTimeZone = zonedTime.withZoneSameInstant(dbZoneId);
        return Timestamp.valueOf(dbTimeZone.toLocalDateTime());
    }


}
