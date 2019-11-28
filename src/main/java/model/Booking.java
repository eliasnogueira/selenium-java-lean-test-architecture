package model;

import enums.RoomType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Booking {

    String email;
    String country;
    @ToString.Exclude String password;
    String dailyBudget;
    Boolean newsletter;
    RoomType roomType;
    String roomDescription;
}
