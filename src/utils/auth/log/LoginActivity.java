package utils.auth.log;

import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;

public class LoginActivity {

    private static final String logPath = "login_activity.txt";

    public static void log(String userName, boolean success, String reason){
        LocalDateTime now = LocalDateTime.now();
        if (userName == null || userName.equals(""))
            userName = "Unknown User";

        String logToWrite = now + " " + userName;
        if (success)
            logToWrite += " login successful. ";
        else
            logToWrite += " login failed. ";

        logToWrite += reason + "\n";
        try {
            FileWriter logWriter = new FileWriter(logPath, true);
            logWriter.write(logToWrite);
            logWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
