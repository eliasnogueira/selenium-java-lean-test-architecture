package test;

import org.testng.annotations.Listeners;
import org.testng.annotations.Test;
import page_objects.ContaPage;
import page_objects.DetalhesPage;
import page_objects.TipoQuartoPage;

import static org.testng.Assert.*;

@Listeners(TestListener.class)
public class ReservarQuartoTest extends BaseTest {

    @Test(description = "Reserva um quarto")
    public void reservarQuarto() {

        ContaPage contaPage = new ContaPage(driver);
        contaPage.preencherEmail("joao.dasilva@gmail.com");
        contaPage.preencherSenha("123456789");
        contaPage.selecionarPais("Brasil");
        contaPage.selecionarOrcamento("R$ 100 - R$ 499");
        contaPage.clicaReceberNewsletter();
        contaPage.proximo();

        TipoQuartoPage quartoPage = new TipoQuartoPage(driver);
        quartoPage.selecionarTipoQuarto(TipoQuartoPage.Quarto.FAMILIA);
        quartoPage.proximo();

        DetalhesPage detalhesPage = new DetalhesPage(driver);
        detalhesPage.preencherDescricaoQuarto("Quarto para não fumantes");
        detalhesPage.terminar();

        assertEquals("Sua reserva já foi feita e logo entraremos em contato!", detalhesPage.getAlertMessage());
    }
}
