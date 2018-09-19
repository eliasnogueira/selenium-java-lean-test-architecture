package page_objects;

import driver.DriverManager;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.pagefactory.AjaxElementLocatorFactory;

import static utils.CommonUtils.getValueFromConfigFile;

public class AbstractPageObject {

    protected AbstractPageObject() {
        int timeout = Integer.parseInt(getValueFromConfigFile("timeout"));
        PageFactory.initElements(new AjaxElementLocatorFactory(DriverManager.getDriver(), timeout), this);
    }
}
