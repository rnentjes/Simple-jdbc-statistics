package nl.astraeus.jdbc.util;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * User: rnentjes
 * Date: 3/28/12
 * Time: 12:28 PM
 */
public class Util {
    // utility functions
    public static String formatNano(long l) {
        NumberFormat format = new DecimalFormat("###,##0.000");

        return format.format((double) l / 1000000.0);
    }

    public static void printMemoryUsage() {
        System.out.println("Used  memory: "+((Runtime.getRuntime().totalMemory() / (1024*1024))-(Runtime.getRuntime().freeMemory() / (1024*1024))));
        System.out.println("Free  memory: "+(Runtime.getRuntime().freeMemory() / (1024*1024)));
        System.out.println("Total memory: "+(Runtime.getRuntime().totalMemory() / (1024*1024)));
        System.out.println("Max   memory: "+(Runtime.getRuntime().maxMemory() / (1024*1024)));
    }

    public static String formatTimestamp(long timeStamp) {
        SimpleDateFormat formatter = new SimpleDateFormat("HH:mm:ss.SSS");

        return formatter.format(new Date(timeStamp));
    }
}
