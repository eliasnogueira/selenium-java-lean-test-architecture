package page_objects.common;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class NavegacaoPage {

    @FindBy(name = "next")
    WebElement proximo;

    @FindBy(name = "previous")
    WebElement anterior;

    @FindBy(name = "finish")
    WebElement terminar;

    public NavegacaoPage(WebDriver driver) {
        PageFactory.initElements(driver, this);
    }

    public void proximo() {
        proximo.click();
    }

    public void anterior() {
        anterior.click();
    }

    public void terminar() {
        terminar.click();
    }
}
