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

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.MediaEntityBuilder;
import driver.DriverFactory;
import driver.DriverManager;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.testng.ITestResult;
import org.testng.annotations.*;
import reporting.ReportManager;
import utils.Log;

import java.io.IOException;
import java.lang.reflect.Method;
import java.util.logging.Level;

import static utils.CommonUtils.*;
public class BaseTest {

    WebDriver driver;

    private static final ThreadLocal<ExtentTest> parentTest = new ThreadLocal<>();
    private static final ThreadLocal<ExtentTest> test = new ThreadLocal<>();
    private ExtentReports extentReports;

    @BeforeSuite
    public void beforeSuite() {
        extentReports = ReportManager.getInstance();
    }

    @BeforeClass
    public synchronized void beforeClass() {
        ExtentTest parent = extentReports.createTest(getClass().getName());
        parentTest.set(parent);
    }

    @BeforeMethod
    @Parameters("browser")
    public void preCondition(@Optional("chrome") String browser, Method method) {
        Log.startLog();

        driver = DriverFactory.createInstance(browser);
        DriverManager.setDriver(driver);

        ExtentTest child = parentTest.get().
                createNode(
                        methodName(method),
                        DriverManager.getInfo());
        test.set(child);

        driver.get(getValueFromConfigFile("url.base"));
    }

    @AfterMethod
    public void postCondition(ITestResult iTestResult) {
        if(iTestResult.getStatus() == ITestResult.FAILURE) {
            failTest(iTestResult);
        } else if (iTestResult.getStatus() == ITestResult.SKIP) {
            test.get().skip(iTestResult.getThrowable());
        } else {
            test.get().pass("Success");
        }

        extentReports.flush();

        Log.endLog();
        DriverManager.quit();
    }

    /**
     * Set the test as failed on ExtentReport.
     * Also add the stack trace and a screenshot
     * @param iTestResult test result
     */
    private void failTest(ITestResult iTestResult) {
        try {
            WebDriver driver = DriverManager.getDriver();

            String base64Screenshot = ((TakesScreenshot)driver).getScreenshotAs(OutputType.BASE64);

            test.get().fail(iTestResult.getThrowable(),
                    MediaEntityBuilder.createScreenCaptureFromBase64String(base64Screenshot).build());
        } catch (IOException e) {
            Log.log(Level.SEVERE, DriverManager.getInfo());
            Log.log(Level.SEVERE, e.getMessage());
        } finally {
            Log.endLog();
        }
    }

    /**
     * Return the test method name. If the @Test has a description it will be get instead of the method name
     * E.g: @Test(description="This is a test description")
     *
     * @param method the method name injected by TestNG
     * @return method name or test description
     */
    private String methodName(Method method) {
        String methodName = method.getAnnotation(Test.class).description();
        if (methodName.isEmpty()) methodName = method.getName();

        return methodName;
    }
}
