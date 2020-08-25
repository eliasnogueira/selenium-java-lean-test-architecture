package model;

import enums.RoomType;

public class BookingBuilder {

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