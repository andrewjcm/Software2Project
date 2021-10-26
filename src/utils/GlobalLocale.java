package utils;

import java.util.Locale;

public class GlobalLocale {
    //private static Locale locale = Locale.CANADA_FRENCH;
    private static Locale locale = Locale.getDefault();

    public static Locale getLocale() {
        return locale;
    }

    public static void setLocale(Locale locale) {
        GlobalLocale.locale = locale;
    }
}
