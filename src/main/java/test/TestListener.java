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
                    new File(getValueFromConfigFile("test.screenshot.path") + System.getProperty("file.separator") + criaNomeDoTeste(iTestResult) + ".png"));

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
    }

    @Override
    public void onFinish(ITestContext iTestContext) {
        // nothing to do
    }


    private String criaNomeDoTeste(ITestResult iTestResult) {
        StringBuilder completeFileName = new StringBuilder();

        completeFileName.append(iTestResult.getTestClass().getRealClass().getSimpleName()); // nome da classe
        completeFileName.append("_");
        completeFileName.append(iTestResult.getName()); // nome do metodo

        // todos os parametros
        Object[] parameters = iTestResult.getParameters();
        for(Object parameter : parameters) {
            completeFileName.append("_");
            completeFileName.append(parameter);
        }

        return completeFileName.toString();
    }
}
