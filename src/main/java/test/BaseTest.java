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

import driver.DriverFactory;
import driver.DriverManager;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;
import utils.Log;

import static utils.CommonUtils.*;
public class BaseTest {

    WebDriver driver;

    @BeforeMethod
    @Parameters("browser")
    public void preCondition(@Optional("chrome") String browser) {
        Log.startLog();

        driver = DriverFactory.createInstance(browser);
        DriverManager.setDriver(driver);

        driver.get(getValueFromConfigFile("url.base"));
    }

    @AfterMethod
    public void postCondition() {
        Log.endLog();
        DriverManager.quit();
    }
}
