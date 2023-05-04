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

package com.eliasnogueira.model;

import com.eliasnogueira.enums.RoomType;

public record Booking(String email, String country, String password, String dailyBudget, Boolean newsletter,
                      RoomType roomType, String roomDescription) {

    public static final class BookingBuilder {

        private String email;
        private String country;
        private String password;
        private String dailyBudget;
        private Boolean newsletter;
        private RoomType roomType;
        private String roomDescription;

        public BookingBuilder email(String email) {
            this.email = email;
            return this;
        }

        public BookingBuilder country(String country) {
            this.country = country;
            return this;
        }

        public BookingBuilder password(String password) {
            this.password = password;
            return this;
        }

        public BookingBuilder dailyBudget(String dailyBudget) {
            this.dailyBudget = dailyBudget;
            return this;
        }

        public BookingBuilder newsletter(Boolean newsletter) {
            this.newsletter = newsletter;
            return this;
        }

        public BookingBuilder roomType(RoomType roomType) {
            this.roomType = roomType;
            return this;
        }

        public BookingBuilder roomDescription(String roomDescription) {
            this.roomDescription = roomDescription;
            return this;
        }

        public Booking build() {
            return new Booking(email, country, password, dailyBudget, newsletter, roomType, roomDescription);
        }
    }
}