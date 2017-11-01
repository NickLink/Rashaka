package com.rashaka.fragments.main.plus.drink;

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

    private int id;

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

    @Override
    public String toString() {
        return "DrinkAlarmItem{" +
                "id=" + id +
                ", enabled=" + enabled +
                ", hours=" + hours +
                ", minutes=" + minutes +
                ", am=" + am +
                ", enMonday=" + enMonday +
                ", enTuesday=" + enTuesday +
                ", enWednesday=" + enWednesday +
                ", enThursday=" + enThursday +
                ", enFriday=" + enFriday +
                ", enSaturday=" + enSaturday +
                ", enSunday=" + enSunday +
                '}';
    }
}
