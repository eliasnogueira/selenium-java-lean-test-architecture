package test;

import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.MediaEntityBuilder;
import driver.DriverManager;
import org.apache.log4j.Logger;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;
import reporting.ReportManager;

import java.io.IOException;

public class TestListener implements ITestListener {

    private static final Logger LOGGER = Logger.getLogger(BaseTest.class.getName());

    private static final ThreadLocal<ExtentTest> parentTest = new ThreadLocal<>();
    private static final ThreadLocal<ExtentTest> test = new ThreadLocal<>();
    private ExtentTest parent;

    @Override
    public void onTestStart(ITestResult result) {
        ExtentTest child = parentTest.get().
                createNode(result.getMethod().getMethodName(), DriverManager.getInfo());
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
        parent = ReportManager.getInstance().createTest(getClass().getName());
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

            test.get().fail(iTestResult.getThrowable(),
                    MediaEntityBuilder.createScreenCaptureFromBase64String(base64Screenshot).build());

            LOGGER.error(DriverManager.getInfo());
            LOGGER.error(iTestResult.getThrowable().getCause());

        } catch (IOException e) {
            LOGGER.error(DriverManager.getInfo());
            LOGGER.error(e.getMessage());
        }
    }

}
