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

package com.eliasnogueira.driver.local;

import com.eliasnogueira.config.ConfigurationManager;
import com.eliasnogueira.driver.IDriver;
import com.eliasnogueira.exceptions.BrowserNotSupportedException;
import com.eliasnogueira.exceptions.HeadlessNotSupportedException;
import io.github.bonigarcia.wdm.WebDriverManager;
import io.github.bonigarcia.wdm.config.DriverManagerType;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.opera.OperaDriver;
import org.openqa.selenium.safari.SafariDriver;

public class LocalDriverManager implements IDriver {

    @Override
    public WebDriver createInstance(String browser) {
        WebDriver driverInstance;
        DriverManagerType driverManagerType = DriverManagerType.valueOf(browser.toUpperCase());
        WebDriverManager.getInstance(driverManagerType).setup();
        boolean isHeadless = ConfigurationManager.getConfiguration().headless();

        switch (driverManagerType) {
            case CHROME:
                driverInstance =
                    isHeadless ? new ChromeDriver(new ChromeOptions().setHeadless(true)) : new ChromeDriver();
                break;
            case FIREFOX:
                driverInstance =
                    isHeadless ? new FirefoxDriver(new FirefoxOptions().setHeadless(true)) : new FirefoxDriver();
                break;
            case OPERA:
                if (isHeadless) headlessNotSupportedForThisBrowser(driverManagerType);
                driverInstance  = new OperaDriver();
                break;
            case EDGE:
                driverInstance =
                    isHeadless ? new EdgeDriver(new EdgeOptions().setHeadless(true)) : new EdgeDriver();
                break;
            case IEXPLORER:
                if (isHeadless) headlessNotSupportedForThisBrowser(driverManagerType);
                driverInstance = new InternetExplorerDriver();
                break;
            case SAFARI:
                if (isHeadless) headlessNotSupportedForThisBrowser(driverManagerType);
                driverInstance = new SafariDriver();
                break;
            default:
                throw new BrowserNotSupportedException(driverManagerType.toString());
        }

        return driverInstance;
    }

    private void headlessNotSupportedForThisBrowser(DriverManagerType driverManagerType) {
        throw new HeadlessNotSupportedException(driverManagerType.toString());
    }
}
