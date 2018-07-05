package test;

import exception.BrowserNotSupportedException;
import org.openqa.selenium.MutableCapabilities;
import org.openqa.selenium.Platform;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.ie.InternetExplorerOptions;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;
import utils.Log;

import java.net.URL;
import java.util.logging.*;

import static utils.CommonUtils.*;

public class BaseTest {

    protected WebDriver driver;
    protected String browser;

    @BeforeMethod
    @Parameters("browser")
    public void preCondicao(@Optional("chrome") String browser) {
        if (browser.isEmpty()) throw new NullPointerException("A variável browser está vazia");

        this.browser = browser;
        Log.startLog();

        driver = getDriver(browser);
        driver.get(getValueFromConfigFile("url.base"));
    }

    @AfterMethod
    public void posCondicao() {
        Log.endLog();
        driver.quit();
    }

    /**
     * Retorna uma nova instancia do RemoteWebDriver baseado na grid apontada
     * @param browser o browser que o teste sera executado
     * @return uma nova instancia RemoteWebDriver instance
     */
    private static WebDriver getDriver(String browser) {
        RemoteWebDriver remoteWebDriver = null;

        try {
            // a composition of the target grid address and port
            String gridURL = "http://" + getValueFromConfigFile("grid.url") + ":" + getValueFromConfigFile("grid.port") + "/wd/hub";

            remoteWebDriver = new RemoteWebDriver(new URL(gridURL), returnCapability(browser));
        } catch (Exception e) {
            Log.log(Level.INFO, "Browser:" +  browser);
            Log.log(Level.SEVERE, e.getMessage(), e);
        }

        return remoteWebDriver;
    }

    /**
     * Retorna um objeto MutableCapabilities para o browser informado
     * @param browser o nome do browser. Browser atualmente disponíveis: chrome, firefox, ie-11
     * @return um objeto MutableCapabilities
     * @throws BrowserNotSupportedException se o browser não estiver mapeado ou escrito errado
     */
    private static MutableCapabilities returnCapability(String browser) throws BrowserNotSupportedException {
        MutableCapabilities capabilities;

        switch (browser.toLowerCase()) {

            case "chrome":
                capabilities = new ChromeOptions();
                ((ChromeOptions) capabilities).addArguments("start-maximized");
                ((ChromeOptions) capabilities).addArguments("lang=pt-BR");
                break;

            case "firefox":
                capabilities = new FirefoxOptions();
                break;

            case "ie-11":
                capabilities = new InternetExplorerOptions();
                capabilities.setCapability(CapabilityType.PLATFORM_NAME, Platform.WINDOWS);
                break;

            default:
                throw new BrowserNotSupportedException("Browser " + browser + "não suportado");
        }

        return capabilities;
    }
}
