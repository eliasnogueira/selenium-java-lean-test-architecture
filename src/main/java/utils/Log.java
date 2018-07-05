package utils;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.logging.*;

import static utils.CommonUtils.*;

public class Log {

    private Log() {}

    private static Logger logger;
    private static Handler handler;

    private static Logger getLogger() {
        if (logger == null) {
            startLog();
        }

        return logger;
    }

    public static void log(Level level, String msg, Throwable thrown) {
        getLogger().log(level, msg, thrown);
    }

    public static void log(Level level, String msg) {
        getLogger().log(level, msg);
    }

    public static void startLog() {
        try {
            logger = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);
            SimpleDateFormat format = new SimpleDateFormat(getValueFromConfigFile("log.dateformat"));
            String fileName = getValueFromConfigFile("log.directory") + System.getProperty("file.separator") + "exceptions_" + format.format(Calendar.getInstance().getTime()) + ".log";

            handler = new FileHandler(fileName, true);
            handler.setLevel(Level.ALL);
            handler.setFormatter(new SimpleFormatter());
            getLogger().addHandler(handler);
        } catch (IOException e) {
            log(Level.SEVERE, e.getMessage(), e);
        }
    }

    public static void endLog() {
        handler.flush();
        handler.close();
    }
}
