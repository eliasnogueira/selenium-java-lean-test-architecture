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

package test;

import com.aventstack.extentreports.testng.listener.ExtentITestListenerClassAdapter;
import config.Configuration;
import driver.DriverFactory;
import driver.DriverManager;
import org.aeonbits.owner.ConfigCache;
import org.aeonbits.owner.ConfigFactory;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.*;

@Listeners({ExtentITestListenerClassAdapter.class, TestListener.class})
public abstract class BaseWeb {

    @BeforeSuite
    @Parameters("environment")
    public void setConfiguration(@Optional("dev") String environment) {
        String env = System.getenv("environment");
        ConfigFactory.setProperty("env", env == null ? environment : env);
    }

    @BeforeMethod
    @Parameters("browser")
    public void preCondition(@Optional("chrome") String browser) {
        Configuration configuration = ConfigCache.getOrCreate(Configuration.class);

        WebDriver driver = DriverFactory.createInstance(browser);
        DriverManager.setDriver(driver);

        DriverManager.getDriver().get(configuration.url());
    }

    @AfterMethod
    public void postCondition() {
        DriverManager.quit();
    }
}
