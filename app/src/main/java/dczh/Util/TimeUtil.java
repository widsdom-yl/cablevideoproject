package dczh.Util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class TimeUtil {
    private static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    private static SimpleDateFormat sdf1 = new SimpleDateFormat("yyyyMMddHHmmss");

    public static String formatDate(Date date) {
        synchronized (sdf) {
            return sdf.format(date);
        }
    }

    public static Date parse(String strDate) {
        synchronized (sdf1) {
            try {
                return sdf1.parse(strDate);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            return null;
        }
    }
}