package config;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Objects;
import java.util.Properties;
import lombok.extern.log4j.Log4j2;

@Log4j2
class ReadConfigFile {

    private ReadConfigFile() {
    }

    /**
     * Return a property value from configuration.properties file
     * @param property a property in the conf file
     * @return the property value
     */
    static String getValueFromConfigFile(String property) {
        Properties properties;

        try {
            properties = new Properties();

            String env = null == System.getProperty("env") ? "dev" : System.getProperty("env");

            String fileSeparator = System.getProperty("file.separator");
            ClassLoader classLoader = ClassLoader.getSystemClassLoader();
            properties.load(new FileReader(new File(Objects.requireNonNull(classLoader.
                getResource("conf" + fileSeparator + env + fileSeparator + "config.properties")).getFile())));

            return properties.getProperty(property);
        } catch (NullPointerException | IOException e) {
            log.error("Property " + property + " was not found on the configuration files", e);
        }
        return null;
    }

}
