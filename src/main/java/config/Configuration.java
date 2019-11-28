package config;

import static config.ReadConfigFile.getValueFromConfigFile;

import java.util.Objects;

public class Configuration {

    public String getBaseURL() {
        return getValueFromConfigFile("url.base");
    }

    public int getTimeout() {
        return Integer.parseInt(Objects.requireNonNull(getValueFromConfigFile("timeout")));
    }

    public String getGridURL() {
        return  getValueFromConfigFile("grid.url");
    }

    public int getGridPort() {
        return Integer.parseInt(Objects.requireNonNull(getValueFromConfigFile("grid.port")));
    }

    public String getFakerLocale() {
        return getValueFromConfigFile("faker.locale");
    }
}
