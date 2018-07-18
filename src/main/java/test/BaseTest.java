/*
 * Copyright 2018 Elias Nogueira
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package test;

import exception.BrowserNotSupportedException;
import org.openqa.selenium.MutableCapabilities;
import org.openqa.selenium.Platform;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.ie.InternetExplorerOptions;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;
import utils.Log;

import java.net.URL;
import java.util.logging.*;

import static utils.CommonUtils.*;
public class BaseTest {

    protected WebDriver driver;
    protected String browser;

    @BeforeMethod
    @Parameters("browser")
    public void preCondicao(@Optional("chrome") String browser) {
        if (browser.isEmpty()) throw new NullPointerException("browser variable is empty");

        this.browser = browser;
        Log.startLog();

        driver = getDriver(browser);
        driver.get(getValueFromConfigFile("url.base"));
    }

    @AfterMethod
    public void posCondicao() {
        Log.endLog();
        driver.quit();
    }

    /**
     * Create a new RemoteWebDriver instance based on the grid URL
     * @param browser the target browser
     * @return a new RemoteWebDriver instance
     */
    private static WebDriver getDriver(String browser) {
        RemoteWebDriver remoteWebDriver = null;

        try {
            // a composition of the target grid address and port
            String gridURL = "http://" + getValueFromConfigFile("grid.url") + ":" + getValueFromConfigFile("grid.port") + "/wd/hub";

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
     * @throws BrowserNotSupportedException if the browser is not mapped or misspelled
     */
    private static MutableCapabilities returnCapability(String browser) throws BrowserNotSupportedException {
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
                throw new BrowserNotSupportedException("Browser " + browser + "not supported");
        }

        return capabilities;
    }

    private static MutableCapabilities defaultChromeOptions() {
        ChromeOptions capabilities = new ChromeOptions();
        capabilities.addArguments("start-maximized");
        capabilities.addArguments("lang=pt-BR");

        return capabilities;
    }
}
