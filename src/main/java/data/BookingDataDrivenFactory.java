package data;

import com.github.javafaker.Faker;
import config.Configuration;
import config.ConfigurationManager;
import enums.RoomType;
import lombok.extern.log4j.Log4j2;
import model.Booking;


import java.util.Locale;
import java.util.Random;

@Log4j2
public class BookingDataDrivenFactory {

    private final Faker faker;

    public BookingDataDrivenFactory() {
        Configuration configuration = ConfigurationManager.getConfiguration();
        faker = new Faker(new Locale(configuration.faker()));
    }

    public Booking createBookingData(String ddEmail, String ddPassword) {
        Booking booking = Booking.builder().
                email(ddEmail).
                country(returnRandomCountry()).
                password(ddPassword).
                dailyBudget(returnDailyBudget()).
                newsletter(faker.bool().bool()).
                roomType(RoomType.getRandom()).
                roomDescription(faker.lorem().paragraph()).
                build();

        log.info(booking);
        return booking;
    }

    private String returnRandomCountry() {
        return returnRandomItemOnArray(new String[]{"Belgium", "Brazil", "Netherlands"});
    }

    private String returnDailyBudget() {
        return returnRandomItemOnArray(new String[]{"$100", "$100 - $499", "$499 - $999", "$999+"});
    }

    private String returnRandomItemOnArray(String[] array) {
        return array[(new Random().nextInt(array.length))];
    }
}
