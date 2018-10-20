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

import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.MediaEntityBuilder;
import driver.DriverManager;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;
import reporting.ReportManager;

import java.io.IOException;

public class TestListener implements ITestListener {

    private static final Logger LOGGER = LogManager.getLogger();

    private static final ThreadLocal<ExtentTest> parentTest = new ThreadLocal<>();
    private static final ThreadLocal<ExtentTest> test = new ThreadLocal<>();

    @Override
    public void onTestStart(ITestResult result) {
        // get the method name from iTestResult
        ExtentTest child = parentTest.get().createNode(result.getMethod().getMethodName(), DriverManager.getInfo());
        test.set(child);
    }

    @Override
    public void onTestSuccess(ITestResult result) {
        test.get().pass("Success");
    }

    @Override
    public void onTestFailure(ITestResult result) {
        failTest(result);
    }

    @Override
    public void onTestSkipped(ITestResult result) {
        test.get().skip(result.getThrowable());
    }

    @Override
    public void onTestFailedButWithinSuccessPercentage(ITestResult result) {
        // do nothing
    }

    @Override
    public void onStart(ITestContext context) {
        // get the test name from iTestContext
        ExtentTest parent = ReportManager.getInstance().createTest(context.getAllTestMethods()[0].getRealClass().getSimpleName());
        parentTest.set(parent);
    }

    @Override
    public void onFinish(ITestContext context) {
        ReportManager.getInstance().flush();
    }

    private void failTest(ITestResult iTestResult) {
        try {
            WebDriver driver = DriverManager.getDriver();

            String base64Screenshot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.BASE64);

            LOGGER.error(iTestResult.getTestClass().getName());
            LOGGER.error(DriverManager.getInfo());
            LOGGER.error(iTestResult.getThrowable());

            test.get().fatal(iTestResult.getThrowable(),
                    MediaEntityBuilder.createScreenCaptureFromBase64String(base64Screenshot).build());

        } catch (IOException e) {
            test.get().fail(e);

            LOGGER.error(iTestResult.getTestClass().getName());
            LOGGER.error(DriverManager.getInfo());
            LOGGER.error(e.getMessage());
        }
    }

}
