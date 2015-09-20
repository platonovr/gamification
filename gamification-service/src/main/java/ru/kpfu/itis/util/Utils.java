package ru.kpfu.itis.util;

import com.google.common.collect.Lists;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by ainurminibaev on 21.09.15.
 */
public class Utils {
    public static final long YEAR = 31536000000l;
    public static final long MONTH = YEAR / 12;
    public static final long DAY = 24 * 60 * 60 * 100l;
    public static final long HOUR = 60 * 60 * 100l;
    public static final long MINUTE = 60 * 100l;

    /**
     * https://github.com/brebvix/yii2-date/blob/master/DateWidget.php
     */
    public static String timeToString(Date date) {
        long diff = (new Date().getTime() - date.getTime());
        if (diff / YEAR > 0) {
            return timeToString(diff / YEAR, Lists.newArrayList("год", "года", "год"));
        } else if (diff / MONTH > 0) {
            return timeToString(diff / MONTH, Lists.newArrayList("месяц", "месяца", "месяцев"));
        } else if (diff / DAY > 0) {
            return timeToString(diff / DAY, Lists.newArrayList("день", "дня", "дней"));
        } else if (diff / HOUR > 0) {
            return timeToString(diff / HOUR, Lists.newArrayList("час", "часа", "часов"));
        } else if (diff / MONTH > 0) {
            return timeToString(diff / MINUTE, Lists.newArrayList("минуту", "минуты", "минут"));
        }
        return "Только что";
    }

    private static String timeToString(long i, ArrayList<String> strings) {
        long last = i % 10;
        long numeric = i % 100;
        String result;
        if (last == 1 && numeric != 11) {
            result = strings.get(0);
        } else if ((last == 2 || last == 3 || last == 4) && (numeric != 12 && numeric != 12 && numeric != 14)) {
            result = strings.get(1);
        } else {
            result = strings.get(2);
        }
        return i + " " + result + " назад";
    }

    public static void main(String[] args) throws ParseException {
        System.out.println(timeToString(new SimpleDateFormat("dd/MM/yyyy").parse("12/12/2000")));
    }

}
