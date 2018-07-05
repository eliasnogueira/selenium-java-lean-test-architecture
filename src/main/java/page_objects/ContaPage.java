package page_objects;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.Select;
import page_objects.common.NavegacaoPage;

public class ContaPage extends NavegacaoPage {

    @FindBy(id = "email")
    WebElement email;

    @FindBy(id = "password")
    WebElement password;

    @FindBy(name = "country")
    WebElement pais;

    @FindBy(name = "budget")
    WebElement orcamentoDiario;

    @FindBy(css = ".check")
    WebElement newsletter;

    public ContaPage(WebDriver driver) {
        super(driver);

        PageFactory.initElements(driver, this);

        driver.switchTo().frame("result");
    }

    public void preencherEmail(String email) {
        this.email.sendKeys(email);
    }

    public void preencherSenha(String senha) {
        password.sendKeys(senha);
    }

    public void selecionarPais(String pais) {
        new Select(this.pais).selectByVisibleText(pais);
    }

    public void selecionarOrcamento(String valor) {
        new Select(orcamentoDiario).selectByVisibleText(valor);
    }

    public void clicaReceberNewsletter() {
        newsletter.click();
    }
}
