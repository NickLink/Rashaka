package com.rashaka.fragments.main.plus.drink;

import android.os.Parcel;
import android.os.Parcelable;

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
public class DrinkAlarmItem implements Parcelable {

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


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.id);
        dest.writeByte(this.enabled ? (byte) 1 : (byte) 0);
        dest.writeInt(this.hours);
        dest.writeInt(this.minutes);
        dest.writeInt(this.am);
        dest.writeByte(this.enMonday ? (byte) 1 : (byte) 0);
        dest.writeByte(this.enTuesday ? (byte) 1 : (byte) 0);
        dest.writeByte(this.enWednesday ? (byte) 1 : (byte) 0);
        dest.writeByte(this.enThursday ? (byte) 1 : (byte) 0);
        dest.writeByte(this.enFriday ? (byte) 1 : (byte) 0);
        dest.writeByte(this.enSaturday ? (byte) 1 : (byte) 0);
        dest.writeByte(this.enSunday ? (byte) 1 : (byte) 0);
    }

    public DrinkAlarmItem(Parcel in) {
        this.id = in.readInt();
        this.enabled = in.readByte() != 0;
        this.hours = in.readInt();
        this.minutes = in.readInt();
        this.am = in.readInt();
        this.enMonday = in.readByte() != 0;
        this.enTuesday = in.readByte() != 0;
        this.enWednesday = in.readByte() != 0;
        this.enThursday = in.readByte() != 0;
        this.enFriday = in.readByte() != 0;
        this.enSaturday = in.readByte() != 0;
        this.enSunday = in.readByte() != 0;
    }

    public static final Parcelable.Creator<DrinkAlarmItem> CREATOR = new Parcelable.Creator<DrinkAlarmItem>() {
        @Override
        public DrinkAlarmItem createFromParcel(Parcel source) {
            return new DrinkAlarmItem(source);
        }

        @Override
        public DrinkAlarmItem[] newArray(int size) {
            return new DrinkAlarmItem[size];
        }
    };
}
