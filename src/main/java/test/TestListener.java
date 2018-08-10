/*
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

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;
import utils.Log;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Field;
import java.util.logging.Level;

import static utils.CommonUtils.*;

public class TestListener implements ITestListener {

    @Override
    public void onTestStart(ITestResult iTestResult) {
        // nothing to do
    }

    @Override
    public void onTestSuccess(ITestResult iTestResult) {
        // nothing to do
    }

    @Override
    public void onTestFailure(ITestResult iTestResult) {
        Throwable throwable = iTestResult.getThrowable();
        String browser;

        Class clazz = iTestResult.getTestClass().getRealClass().getSuperclass();
        try {
            Field fieldDriver = clazz.getDeclaredField("driver");
            Field fieldBrowser = clazz.getDeclaredField("browser");
            fieldDriver.setAccessible(true);
            fieldBrowser.setAccessible(true);

            WebDriver driver = (WebDriver) fieldDriver.get(iTestResult.getInstance());
            browser = (String) fieldBrowser.get(iTestResult.getInstance());

            File file = ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);

            // the filename is the folder name on test.screenshot.path property plus the completeTestName
            FileUtils.copyFile(file,
                    new File(getValueFromConfigFile("test.screenshot.path") + System.getProperty("file.separator") + createTestName(iTestResult) + ".png"));

            Log.log(Level.INFO, "Browser: " + browser);
            Log.log(Level.SEVERE, throwable.getMessage(), throwable);

        } catch (NoSuchFieldException | IllegalAccessException | IOException  e) {
            Log.log(Level.SEVERE, e.getMessage(), e);
        }
    }

    @Override
    public void onTestSkipped(ITestResult iTestResult) {
        // nothing to do
    }

    @Override
    public void onTestFailedButWithinSuccessPercentage(ITestResult iTestResult) {
        // nothing to do
    }

    @Override
    public void onStart(ITestContext iTestContext) {
        // nothing to do
        // initialize extent
    }

    @Override
    public void onFinish(ITestContext iTestContext) {
        // nothing to do
        // finalize extent
    }


    private String createTestName(ITestResult iTestResult) {
        StringBuilder completeFileName = new StringBuilder();

        completeFileName.append(iTestResult.getTestClass().getRealClass().getSimpleName()); // class name
        completeFileName.append("_");
        completeFileName.append(iTestResult.getName()); // method name

        // all parameters
        Object[] parameters = iTestResult.getParameters();
        for(Object parameter : parameters) {
            completeFileName.append("_");
            completeFileName.append(parameter);
        }

        return completeFileName.toString();
    }
}
