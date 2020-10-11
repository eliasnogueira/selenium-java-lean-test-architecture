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

package com.eliasnogueira.driver.remote;

import com.eliasnogueira.config.Configuration;
import com.eliasnogueira.config.ConfigurationManager;
import com.eliasnogueira.driver.IDriver;
import com.eliasnogueira.exceptions.BrowserNotSupportedException;
import io.github.bonigarcia.wdm.config.DriverManagerType;
import java.net.MalformedURLException;
import java.net.URL;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.MutableCapabilities;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.ie.InternetExplorerOptions;
import org.openqa.selenium.opera.OperaOptions;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.safari.SafariOptions;

public class RemoteDriverManager implements IDriver {

    private static final Logger logger = LogManager.getLogger(RemoteDriverManager.class);

    @Override
    public WebDriver createInstance(String browser) {
        RemoteWebDriver remoteWebDriver = null;
        Configuration configuration = ConfigurationManager.getConfiguration();
        try {
            // a composition of the target grid address and port
            String gridURL = String.format("http://%s:%s/wd/hub", configuration.gridUrl(), configuration.gridPort());

            remoteWebDriver = new RemoteWebDriver(new URL(gridURL), getCapability(browser));
        } catch (MalformedURLException e) {
            logger.error("Grid URL is invalid or Grid is not available");
            logger.error(String.format("Browser: %s", browser), e);
        } catch (IllegalArgumentException e) {
            logger.error(String.format("Browser %s is not valid or recognized", browser), e);
        }

        return remoteWebDriver;
    }

    private static MutableCapabilities getCapability(String browser) {
        MutableCapabilities mutableCapabilities;
        DriverManagerType driverManagerType = DriverManagerType.valueOf(browser.toUpperCase());

        switch (driverManagerType) {

            case CHROME:
                mutableCapabilities = defaultChromeOptions();
                break;
            case FIREFOX:
                mutableCapabilities = new FirefoxOptions();
                break;
            case OPERA:
                mutableCapabilities = new OperaOptions();
                break;
            case EDGE:
                mutableCapabilities = new EdgeOptions();
                break;
            case IEXPLORER:
                mutableCapabilities = new InternetExplorerOptions();
                break;
            case SAFARI:
                mutableCapabilities = new SafariOptions();
                break;
            default:
                throw new BrowserNotSupportedException(driverManagerType.toString());
        }

        return mutableCapabilities;
    }

    private static MutableCapabilities defaultChromeOptions() {
        ChromeOptions capabilities = new ChromeOptions();
        capabilities.addArguments("start-maximized");

        return capabilities;
    }
}
