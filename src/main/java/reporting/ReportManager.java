package reporting;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;

import java.io.File;

import static utils.CommonUtils.getValueFromConfigFile;

public class ReportManager {

    private static ExtentReports extentReports;

    private ReportManager() {
    }

    public static ExtentReports getInstance() {
        if (extentReports == null) {
            createInstance();
        }

        return extentReports;
    }

    private static void createInstance() {
        createReportDir();

        ExtentHtmlReporter extentHtmlReporter = new ExtentHtmlReporter(
                new File(getValueFromConfigFile("report.dir") + File.separator + "execution.html"));
        extentReports = new ExtentReports();
        extentReports.attachReporter(extentHtmlReporter);

        extentReports.setSystemInfo("OS", System.getProperty("os.name"));
        extentReports.setSystemInfo("Targer enviromnent", null == System.getProperty("env") ? "dev" : System.getProperty("env"));

        extentHtmlReporter.config().setChartVisibilityOnOpen(true);
        extentHtmlReporter.config().setDocumentTitle(getValueFromConfigFile("report.title"));
        extentHtmlReporter.config().setReportName(getValueFromConfigFile("report.name"));
        extentHtmlReporter.config().setTheme(Theme.DARK);
        extentHtmlReporter.config().setEncoding("UTF-8");
        extentHtmlReporter.config().setTimeStampFormat(getValueFromConfigFile("report.timeStampFormat"));
    }

    private static void createReportDir() {
        String reportDir = getValueFromConfigFile("report.dir");
        File directory = new File(reportDir);

        if (!directory.exists()) directory.mkdir();
    }
}
