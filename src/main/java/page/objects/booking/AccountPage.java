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

package page.objects.booking;

import driver.DriverManager;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.Select;
import page.objects.booking.common.NavigationPage;

public class AccountPage extends NavigationPage {

    @FindBy(id = "email")
    private WebElement email;

    @FindBy(id = "password")
    private WebElement password;

    @FindBy(name = "country")
    private WebElement country;

    @FindBy(name = "budget")
    private WebElement budget;

    @FindBy(css = ".check")
    private WebElement newsletter;

    public AccountPage() {
        DriverManager.getDriver().switchTo().frame("result");
    }

    public void fillEmail(String email) {
        this.email.sendKeys(email);
    }

    public void fillPassword(String password) {
        this.password.sendKeys(password);
    }

    public void selectCountry(String country) {
        new Select(this.country).selectByVisibleText(country);
    }

    public void selectBudget(String value) {
        new Select(budget).selectByVisibleText(value);
    }

    public void clickNewsletter() {
        newsletter.click();
    }
}
