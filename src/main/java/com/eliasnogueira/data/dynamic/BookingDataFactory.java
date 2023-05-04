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

package com.eliasnogueira.data.dynamic;

import com.eliasnogueira.enums.RoomType;
import com.eliasnogueira.model.Booking;
import net.datafaker.Faker;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Locale;

import static com.eliasnogueira.config.ConfigurationManager.configuration;

public final class BookingDataFactory {

    private static final Faker faker = new Faker(new Locale(configuration().faker()));
    private static final Logger logger = LogManager.getLogger(BookingDataFactory.class);

    private BookingDataFactory() {
    }

    public static Booking createBookingData() {
        var booking = new Booking.BookingBuilder().
            email(faker.internet().emailAddress()).
            country(returnRandomCountry()).
            password(faker.internet().password()).
            dailyBudget(returnDailyBudget()).
            newsletter(faker.bool().bool()).
            roomType(RoomType.getRandom()).
            roomDescription(faker.lorem().paragraph()).
            build();

        logger.info(booking);
        return booking;
    }

    private static String returnRandomCountry() {
        return faker.options().option("Belgium", "Brazil", "Netherlands");
    }

    private static String returnDailyBudget() {
        return faker.options().option("$100", "$100 - $499", "$499 - $999", "$999+");
    }
}
