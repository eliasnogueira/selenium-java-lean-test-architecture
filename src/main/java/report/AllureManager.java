package report;

import com.github.automatedowl.tools.AllureEnvironmentWriter;
import com.google.common.collect.ImmutableMap;
import config.Configuration;
import config.ConfigurationManager;
import driver.DriverManager;
import io.qameta.allure.Attachment;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;

public class AllureManager {

    private AllureManager() {}

    public static void setAllureEnvironmentInformation() {
        Configuration configuration = ConfigurationManager.getConfiguration();

        AllureEnvironmentWriter.allureEnvironmentWriter(
            ImmutableMap.<String, String>builder().
                put("URL", configuration.url()).
                put("Target", configuration.target()).
                build());
    }

    @Attachment(value = "Failed test screenshot", type = "image/png")
    public static byte[] takeScreenshotToAttachOnAllureReport() {
        return ((TakesScreenshot) DriverManager.getDriver()).getScreenshotAs(OutputType.BYTES);
    }

    @Attachment(value = "Browser information", type = "text/plain")
    public static String addBrowserInformationOnAllureReport() {
        return DriverManager.getInfo();
    }

}
