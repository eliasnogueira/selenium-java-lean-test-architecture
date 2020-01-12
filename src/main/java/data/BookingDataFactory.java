package data;

import com.aventstack.extentreports.service.ExtentTestManager;
import com.github.javafaker.Faker;
import config.Configuration;
import enums.RoomType;
import java.util.Locale;
import java.util.Random;
import lombok.extern.log4j.Log4j2;
import model.Booking;
import org.aeonbits.owner.ConfigCache;

@Log4j2
public class BookingDataFactory {

    private final Faker faker;

    public BookingDataFactory() {
        Configuration configuration = ConfigCache.getOrCreate(Configuration.class);
        faker = new Faker(new Locale(configuration.faker()));
    }

    public Booking createBookingData() {
        Booking booking = Booking.builder().
            email(faker.internet().emailAddress()).
            country(returnRandomCountry()).
            password(faker.internet().password()).
            dailyBudget(returnDailyBudget()).
            newsletter(faker.bool().bool()).
            roomType(RoomType.getRandom()).
            roomDescription(faker.lorem().paragraph()).
            build();

        log.info(booking);
        ExtentTestManager.getTest().info(booking.toString());
        return booking;
    }

    private String returnRandomCountry() {
        return returnRandomItemOnArray(new String[] {"Belgium", "Brazil", "Netherlands"});
    }

    private String returnDailyBudget() {
        return returnRandomItemOnArray(new String[] {"$100", "$100 - $499", "$499 - $999", "$999+"});
    }

    private String returnRandomItemOnArray(String[] array) {
        return array[(new Random().nextInt(array.length))];
    }
}
