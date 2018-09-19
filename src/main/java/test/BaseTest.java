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

import com.github.javafaker.Faker;
import driver.DriverFactory;
import driver.DriverManager;
import org.openqa.selenium.WebDriver;
import org.testng.ITestResult;
import org.testng.annotations.*;

import java.lang.reflect.Method;
import java.util.Locale;

import static utils.CommonUtils.getValueFromConfigFile;

@Listeners(TestListener.class)
public class BaseTest {

    protected Faker faker;

    @BeforeMethod
    @Parameters("browser")
    public void preCondition(@Optional("chrome") String browser, Method method) {
        faker = new Faker(new Locale(getValueFromConfigFile("faker.locale")));

        WebDriver driver = DriverFactory.createInstance(browser);
        DriverManager.setDriver(driver);

        DriverManager.getDriver().get(getValueFromConfigFile("url.base"));
    }

    @AfterMethod
    public void postCondition(ITestResult iTestResult) {
        DriverManager.quit();
    }
}
