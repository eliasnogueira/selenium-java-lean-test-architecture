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

package driver.remote;

import config.Configuration;
import driver.IDriver;
import io.github.bonigarcia.wdm.DriverManagerType;
import lombok.extern.log4j.Log4j2;
import org.aeonbits.owner.ConfigCache;
import org.openqa.selenium.MutableCapabilities;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.ie.InternetExplorerOptions;
import org.openqa.selenium.opera.OperaOptions;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.net.MalformedURLException;
import java.net.URL;

@Log4j2
public class RemoteDriverManager implements IDriver {

    @Override
    public WebDriver createInstance(String browser) {
        RemoteWebDriver remoteWebDriver = null;
        Configuration configuration = ConfigCache.getOrCreate(Configuration.class);
        try {
            // a composition of the target grid address and port
            String gridURL = String.format("http://%s:%s/wd/hub", configuration.gridUrl(), configuration.gridPort());

            remoteWebDriver = new RemoteWebDriver(new URL(gridURL), getCapability(browser));
        } catch (MalformedURLException e) {
            log.error("Grid URL is invalid or Grid is not available");
            log.error("Browser: " +  browser, e);
        } catch (IllegalArgumentException e) {
            log.error("Browser: " +  browser + "is not valid or recognized", e);
        }

        return remoteWebDriver;
    }

    private static MutableCapabilities getCapability(String browser) {
        MutableCapabilities mutableCapabilities;
        DriverManagerType driverManagerType = DriverManagerType.valueOf(browser.toUpperCase());

        switch (driverManagerType) {

            case CHROME:
                mutableCapabilities =  defaultChromeOptions();
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
            case PHANTOMJS:
            case SELENIUM_SERVER_STANDALONE:
                throw new IllegalStateException("Not supported: " + driverManagerType);
            default:
                throw new IllegalStateException("Unexpected value: " + driverManagerType);
        }

        return mutableCapabilities;
    }

    private static MutableCapabilities defaultChromeOptions() {
        ChromeOptions capabilities = new ChromeOptions();
        capabilities.addArguments("start-maximized");

        return capabilities;
    }
}
