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

public class Booking {

    private String email;
    private String country;
    private String password;
    private String dailyBudget;
    private Boolean newsletter;
    private RoomType roomType;
    private String roomDescription;

    public Booking(String email, String country, String password, String dailyBudget, Boolean newsletter,
                   RoomType roomType, String roomDescription) {
        this.email = email;
        this.country = country;
        this.password = password;
        this.dailyBudget = dailyBudget;
        this.newsletter = newsletter;
        this.roomType = roomType;
        this.roomDescription = roomDescription;
    }

    public Booking() {
    }

    public String getEmail() {
        return this.email;
    }

    public String getCountry() {
        return this.country;
    }

    public String getPassword() {
        return this.password;
    }

    public String getDailyBudget() {
        return this.dailyBudget;
    }

    public Boolean getNewsletter() {
        return this.newsletter;
    }

    public String getRoomType() {
        return this.roomType.get();
    }

    public String getRoomDescription() {
        return this.roomDescription;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setDailyBudget(String dailyBudget) {
        this.dailyBudget = dailyBudget;
    }

    public void setNewsletter(Boolean newsletter) {
        this.newsletter = newsletter;
    }

    public void setRoomType(RoomType roomType) {
        this.roomType = roomType;
    }

    public void setRoomDescription(String roomDescription) {
        this.roomDescription = roomDescription;
    }

    public String toString() {
        return "Booking(email=" + this.getEmail() + ", country=" + this.getCountry() + ", dailyBudget=" +
                this.getDailyBudget() + ", newsletter=" + this.getNewsletter() + ", roomType="
                + this.getRoomType() + ", roomDescription=" + this.getRoomDescription() + ")";
    }

}
