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

            test.get().fatal(iTestResult.getThrowable(),
                    MediaEntityBuilder.createScreenCaptureFromBase64String(base64Screenshot).build());

            LOGGER.error(DriverManager.getInfo());
            LOGGER.error(iTestResult.getThrowable().getCause());

        } catch (IOException e) {
            test.get().fail(e);

            LOGGER.error(DriverManager.getInfo());
            LOGGER.error(e.getMessage());
        }
    }

}
