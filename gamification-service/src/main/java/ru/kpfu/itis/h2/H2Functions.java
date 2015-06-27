package ru.kpfu.itis.h2;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Calendar;

public class H2Functions {

    private static final Logger logger = LoggerFactory.getLogger(H2Functions.class);

    public static String version() {
        try {
            Class targetClass = Class.forName("org.h2.engine.Constants");
            Method method = ReflectionUtils.findMethod(targetClass, "getFullVersion");
            return "H2 " + method.invoke(targetClass);

        } catch (ClassNotFoundException e) {
            //do nothing

        } catch (InvocationTargetException e) {
            logger.debug(e.getMessage(), e);

        } catch (IllegalAccessException e) {
            logger.debug(e.getMessage(), e);
        }
        return "Unknown";
    }

    public static java.sql.Date date(java.util.Date date) {
        return new java.sql.Date(date.getTime());
    }

    public static int date_part(String param, java.util.Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar.get(Calendar.HOUR_OF_DAY);
    }

    public static Object ST_SetSRID(Object geometry, Integer srid) {
        return null;
    }

    public static Object ST_MakePoint(Double lon, Double lat) {
        return null;
    }

    public static int ST_Distance_Sphere(Object pointA, Object pointB) {
        return Integer.MAX_VALUE;
    }

    public static boolean ST_Contains(Object geometryA, Object geometryB) {
        return false;
    }

    public static Object StandGeometry(String json) {
        return null;
    }

}
