package driver.local;

import driver.IDriver;
import io.github.bonigarcia.wdm.DriverManagerType;
import io.github.bonigarcia.wdm.WebDriverManager;
import lombok.extern.log4j.Log4j2;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.opera.OperaDriver;

@Log4j2
public class LocalDriver implements IDriver {

    @Override
    public WebDriver createInstance(String browser) {
         WebDriver driver = null;

        try {
            DriverManagerType driverManagerType = DriverManagerType.valueOf(browser.toUpperCase());
            Class<? extends WebDriver> driverClass = driverResolver(driverManagerType);
            WebDriverManager.getInstance(driverManagerType).setup();
            driver = driverClass.newInstance();
        } catch (InstantiationException | IllegalAccessException e) {
            log.error("Problem during instantiation the driver", e);
        }
        return driver;
    }

    private Class driverResolver(DriverManagerType driverManagerType) {
        Class clazz;

        switch (driverManagerType) {
            case CHROME:
                clazz = ChromeDriver.class;
                break;
            case FIREFOX:
                clazz = FirefoxDriver.class;
                break;
            case OPERA:
                clazz = OperaDriver.class;
                break;
            case EDGE:
                clazz = EdgeDriver.class;
                break;
            case PHANTOMJS:
            case SELENIUM_SERVER_STANDALONE:
                throw new IllegalStateException("Not supported: " + driverManagerType);
            case IEXPLORER:
                clazz = InternetExplorerDriver.class;
                break;
            default:
                throw new IllegalStateException("Unexpected value: " + driverManagerType);
        }
        return clazz;
    }
}
