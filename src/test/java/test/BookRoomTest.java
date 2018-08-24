/*
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
package test;

import enums.Room;
import org.testng.annotations.Test;
import page_objects.booking.AccountPage;
import page_objects.booking.DetailPage;
import page_objects.booking.RoomPage;

import static org.testng.Assert.assertEquals;

public class BookRoomTest extends BaseTest {

    @Test(description = "Book a room")
    public void bookARoom() {

        AccountPage accountPage = new AccountPage();
        accountPage.fillEmail("joao.dasilva@gmail.com");
        accountPage.fillPassword("123456789");
        accountPage.selectCountry("Brasil");
        accountPage.selectBudget("$100 - $499");
        accountPage.clickNewsletter();
        accountPage.next();

        RoomPage roomPage = new RoomPage();
        roomPage.selectRoomType(Room.FAMILY);
        roomPage.next();

        DetailPage detailPage = new DetailPage();
        detailPage.fillRoomDescription("No smoking room");
        detailPage.finish();

        assertEquals(detailPage.getAlertMessage(),
                "Your reservation has been made and we will contact you shortly");
    }
}
