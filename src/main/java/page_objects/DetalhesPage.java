package page_objects;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.pagefactory.AjaxElementLocatorFactory;
import page_objects.common.NavegacaoPage;

public class DetalhesPage extends NavegacaoPage {

    private WebDriver driver;

    @FindBy(id = "description")
    WebElement descricaoQuarto;

    @FindBy(css = "#message > p")
    WebElement message;

    public DetalhesPage(WebDriver driver) {
        super(driver);

        this.driver = driver;
        PageFactory.initElements(new AjaxElementLocatorFactory(driver, 5), this);
    }

    public void preencherDescricaoQuarto(String descricao) {
        new Actions(driver).sendKeys(descricaoQuarto, descricao);
    }

    public String getAlertMessage() {
        return message.getText();
    }
}
