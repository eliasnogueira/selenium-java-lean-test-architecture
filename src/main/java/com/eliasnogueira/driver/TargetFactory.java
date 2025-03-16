/*
 * MIT License
 *
 * Copyright (c) 2021 Elias Nogueira
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

package com.eliasnogueira.driver;

import com.eliasnogueira.enums.Target;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.MutableCapabilities;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.net.URI;

import static com.eliasnogueira.config.ConfigurationManager.configuration;
import static com.eliasnogueira.driver.BrowserFactory.valueOf;
import static java.lang.String.format;

public class TargetFactory {

    private static final Logger logger = LogManager.getLogger(TargetFactory.class);

    public WebDriver createInstance(String browser) {
        Target target = Target.get(configuration().target().toUpperCase());

        return switch (target) {
            case LOCAL -> valueOf(configuration().browser().toUpperCase()).createLocalDriver();
            case LOCAL_SUITE -> valueOf(browser.toUpperCase()).createLocalDriver();
            case SELENIUM_GRID -> createRemoteInstance(valueOf(browser.toUpperCase()).getOptions());
            case TESTCONTAINERS -> valueOf(configuration().browser().toUpperCase()).createTestContainerDriver();
        };
    }

    private RemoteWebDriver createRemoteInstance(MutableCapabilities capability) {
        RemoteWebDriver remoteWebDriver = null;
        try {
            String gridURL = format("http://%s:%s", configuration().gridUrl(), configuration().gridPort());

            remoteWebDriver = new RemoteWebDriver(URI.create(gridURL).toURL(), capability);
        } catch (java.net.MalformedURLException e) {
            logger.error("Grid URL is invalid or Grid is not available");
            logger.error("Browser: {}", capability.getBrowserName(), e);
        } catch (IllegalArgumentException e) {
            logger.error("Browser {} is not valid or recognized", capability.getBrowserName(), e);
        }

        return remoteWebDriver;
    }
}
