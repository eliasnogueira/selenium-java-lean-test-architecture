/*
 * Copyright (c) 2018 Elias Nogueira
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */
package utils;

import java.io.File;
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
            String logDirectory = getValueFromConfigFile("log.directory");
            File directory = new File(logDirectory);

            if (!directory.exists()) directory.mkdir();

            logger = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);
            SimpleDateFormat format = new SimpleDateFormat(getValueFromConfigFile("log.dateformat"));
            String fileName = logDirectory + System.getProperty("file.separator") + "exceptions_" + format.format(Calendar.getInstance().getTime()) + ".log";

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
