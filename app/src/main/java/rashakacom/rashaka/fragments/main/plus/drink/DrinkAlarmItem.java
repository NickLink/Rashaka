package rashakacom.rashaka.fragments.main.plus.drink;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Created by User on 05.09.2017.
 */

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class DrinkAlarmItem {

    private boolean enabled;

    private int hours;

    private int minutes;

    private int am;

    private boolean enMonday;

    private boolean enTuesday;

    private boolean enWednesday;

    private boolean enThursday;

    private boolean enFriday;

    private boolean enSaturday;

    private boolean enSunday;
}
