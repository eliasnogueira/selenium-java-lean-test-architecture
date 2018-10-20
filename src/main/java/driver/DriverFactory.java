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

package driver;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.MutableCapabilities;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.safari.SafariOptions;

import java.net.MalformedURLException;
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

    private static final Logger LOGGER = LogManager.getLogger();

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
        } catch (MalformedURLException e) {
            LOGGER.error("Grid URL is invalid or Grid is not available");
            LOGGER.error("Browser: " +  browser, e);
        } catch (IllegalArgumentException e) {
            LOGGER.error("Browser: " +  browser + "is not valid or recognized", e);
        }

        return remoteWebDriver;
    }


    private static MutableCapabilities returnCapability(String browser) {
        return valueOf(browser.toUpperCase()).returnDriver();
    }

    @Override
    public String toString() {
        return super.toString().toLowerCase();
    }
}
