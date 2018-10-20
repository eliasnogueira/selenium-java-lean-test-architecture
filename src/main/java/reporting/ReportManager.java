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
