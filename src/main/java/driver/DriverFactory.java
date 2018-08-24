package driver;

import exception.BrowserException;
import org.openqa.selenium.MutableCapabilities;
import org.openqa.selenium.Platform;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.ie.InternetExplorerOptions;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.RemoteWebDriver;
import utils.Log;

import java.net.URL;
import java.util.logging.Level;

import static utils.CommonUtils.getValueFromConfigFile;

public class DriverFactory {

    private DriverFactory() {
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
            Log.log(Level.INFO, "Browser:" +  browser);
            Log.log(Level.SEVERE, e.getMessage(), e);
        }

        return remoteWebDriver;
    }

    /**
     * Return a MutableCapabilities object with the target browser
     * @param browser browser name.
     * @return a MutableCapabilities object
     * @throws BrowserException if the browser is not mapped or misspelled
     */
    private static MutableCapabilities returnCapability(String browser) throws BrowserException {
        MutableCapabilities capabilities;

        switch (browser.toLowerCase()) {

            case "chrome":
                capabilities = defaultChromeOptions();
                break;

            case "chrome-headless":
                capabilities = defaultChromeOptions();
                ((ChromeOptions) capabilities).addArguments("headless");
                break;

            case "firefox":
                capabilities = new FirefoxOptions();
                break;

            case "ie-11":
                capabilities = new InternetExplorerOptions();
                capabilities.setCapability(CapabilityType.PLATFORM_NAME, Platform.WINDOWS);
                break;

            default:
                throw new BrowserException("Browser " + browser + "not supported");
        }

        // this capability disable video recoring if you are using Zalenium
        capabilities.setCapability("recordVideo", getValueFromConfigFile("video.recoring"));

        return capabilities;
    }

    private static MutableCapabilities defaultChromeOptions() {
        ChromeOptions capabilities = new ChromeOptions();
        capabilities.addArguments("start-maximized");
        capabilities.addArguments("lang=pt-BR");

        return capabilities;
    }
}
