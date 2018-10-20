/*
 * MIT License
 *
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

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

public class CommonUtils {

    private static final Logger LOGGER = LogManager.getLogger();

    private CommonUtils() {
    }

    /**
     * Return a property value from conf/${env}/config.properties file
     * @param property a property in config/${env}/config.properties
     * @return the property value
     */
    public static String getValueFromConfigFile(String property) {
        Properties properties;

        try {
            properties = new Properties();

            String env = null == System.getProperty("env") ? "dev" : System.getProperty("env");

            String fileSeparator = System.getProperty("file.separator");
            ClassLoader classLoader = ClassLoader.getSystemClassLoader();
            properties.load(new FileReader(new File(classLoader.
                    getResource("conf" + fileSeparator + env + fileSeparator + "config.properties").getFile())));

            return properties.getProperty(property);
        } catch (NullPointerException | IOException e) {
            LOGGER.error("Property " + property + " was not found on the configuration files", e);
        }
        return null;
    }
}
