package page_objects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import page_objects.common.NavegacaoPage;

public class TipoQuartoPage extends NavegacaoPage {

    private WebDriver driver;

    public TipoQuartoPage(WebDriver driver) {
        super(driver);

        this.driver = driver;
    }


    public void selecionarTipoQuarto(Quarto quarto) {
        driver.findElement(By.xpath("//h6[text()='" + quarto + "']")).click();
    }

    public enum Quarto {

        SINGLE("Single"), FAMILIA("Familia"), BUSINESS("Business");

        private String valor;

        Quarto(String valor) {
            this.valor = valor;
        }

        @Override
        public String toString() {
            return valor;
        }
    }
}
