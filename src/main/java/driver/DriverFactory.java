package driver;

import org.apache.log4j.Logger;
import org.openqa.selenium.MutableCapabilities;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.safari.SafariOptions;

import java.net.URL;

import static utils.CommonUtils.getValueFromConfigFile;

public enum DriverFactory implements IDriverType {

    FIREFOX {
        public MutableCapabilities returnDriver() {
            return new FirefoxOptions();
        }
    },

    FIREFOX_HEADLESS {
        public MutableCapabilities returnDriver() {
            FirefoxOptions firefoxOptions = new FirefoxOptions();
            firefoxOptions.setHeadless(true);
            return firefoxOptions;
        }
    },

    CHROME {
        @Override
        public MutableCapabilities returnDriver() {
            return defaultChromeOptions();
        }
    },

    CHROME_HEADLESS {
        @Override
        public MutableCapabilities returnDriver() {
            return ((ChromeOptions) defaultChromeOptions()).addArguments("headless");
        }
    },

    SAFARI {
        @Override
        public MutableCapabilities returnDriver() {
            return new SafariOptions();
        }
    },

    EDGE {
        @Override
        public MutableCapabilities returnDriver() {
            return new EdgeOptions();
        }
    };

    private static final Logger LOGGER = Logger.getLogger(DriverFactory.class.getName() );

    private static MutableCapabilities defaultChromeOptions() {
        ChromeOptions capabilities = new ChromeOptions();
        capabilities.addArguments("start-maximized");
        capabilities.addArguments("lang=pt-BR");

        return capabilities;
    }

    /**
     * Create a new RemoteWebDriver instance based on the grid URL
     * @param browser the target browser
     * @return a new RemoteWebDriver instance
     */
    public static WebDriver createInstance(String browser) {
        RemoteWebDriver remoteWebDriver = null;

        try {
            // a composition of the target grid address and port
            String gridURL = getValueFromConfigFile("grid.url") + ":" + getValueFromConfigFile("grid.port") + "/wd/hub";

            remoteWebDriver = new RemoteWebDriver(new URL(gridURL), returnCapability(browser));
        } catch (Exception e) {
            LOGGER.error("Browser: " +  browser, e);
        }

        return remoteWebDriver;
    }


    public static MutableCapabilities returnCapability(String browser) {
        return valueOf(browser.toUpperCase()).returnDriver();
    }

    @Override
    public String toString() {
        return super.toString().toLowerCase();
    }
}
