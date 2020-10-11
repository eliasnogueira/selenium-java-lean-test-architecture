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

package com.eliasnogueira.driver;

import com.eliasnogueira.config.Configuration;
import com.eliasnogueira.config.ConfigurationManager;
import com.eliasnogueira.driver.local.LocalDriverManager;
import com.eliasnogueira.driver.remote.RemoteDriverManager;
import com.eliasnogueira.enums.Target;
import com.eliasnogueira.exceptions.TargetNotValidException;
import org.openqa.selenium.WebDriver;

public class DriverFactory implements IDriver {

    public WebDriver createInstance(String browser) {
        Configuration configuration = ConfigurationManager.getConfiguration();
        Target target = Target.valueOf(configuration.target().toUpperCase());
        WebDriver webdriver;

        switch (target) {

            case LOCAL:
                //override the browser value from @Optional on BaseWeb
                webdriver = new LocalDriverManager().createInstance(configuration.browser());
                break;
            case GRID:
                // getting the browser from the suite file or @Optional on BaseWeb
                webdriver = new RemoteDriverManager().createInstance(browser);
                break;
            default:
                throw new TargetNotValidException(target.toString());
        }

        return webdriver;
    }
}
