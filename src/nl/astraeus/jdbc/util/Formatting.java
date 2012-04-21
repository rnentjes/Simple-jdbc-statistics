package nl.astraeus.jdbc.util;

import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * User: rnentjes
 * Date: 4/21/12
 * Time: 12:58 PM
 */
public class Formatting {
    private static String DATE_FORMATTER = "date_formatter";
    private static String TIMESTAMP = "TIMESTAMP";
    private static String NANOTIMESTAMP = "NANOTIMESTAMP";

    private static ThreadLocal<Map<String, Object>> formatters = new ThreadLocal<Map<String, Object>>() {
        @Override
        protected Map<String, Object> initialValue() {
            return new HashMap<String, Object>();
        }
    };

    private static <T> T getFormatter(String name, Class<T> cls) {
        Object result = formatters.get().get(name);

        return (T)result;
    }

    private static void setFormatter(String name, Object formatter) {
        formatters.get().put(name, formatter);
    }

    public static DateFormat getTimeStampFormatter() {
        DateFormat result = getFormatter(TIMESTAMP, DateFormat.class);

        if (result == null) {
            result = new SimpleDateFormat("HH:mm:ss.SSS");
            setFormatter(TIMESTAMP, result);
        }
        return result;
    }

    public static NumberFormat getNanoTimeStampFormatter() {
        NumberFormat result = getFormatter(NANOTIMESTAMP, DecimalFormat.class);

        if (result == null) {
            result = new DecimalFormat("###,##0.000");
            setFormatter(TIMESTAMP, result);
        }
        return result;
    }

    public static String formatDuration(long time) {
        return getTimeStampFormatter().format(new Date(time));
    }

    public static String formatNanoDuration(long time) {
        return getNanoTimeStampFormatter().format((double) time / 1000000.0);
    }

    public static String formatTimestamp(long time) {
        return getTimeStampFormatter().format(new Date(time));
    }

    public static String formatTimestamp(Date date) {
        return getTimeStampFormatter().format(new Date(getMillisecondOfDay(date)));
    }

    public static long getMillisecondOfDay(Date date) {
        long result = 0;

        Calendar c = Calendar.getInstance();
        c.setTime(date);

        result += c.get(Calendar.HOUR_OF_DAY) * 1000L * 60L * 60L;
        result += c.get(Calendar.MINUTE) * 1000L * 60L;
        result += c.get(Calendar.SECOND) * 1000L;
        result += c.get(Calendar.MILLISECOND);

        return result;
    }



}
