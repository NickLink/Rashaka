package com.rashaka.domain.steps;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Created by User on 20.10.2017.
 */

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class StepsListItem {

    @SerializedName("id")
    @Expose
    int id;

    @SerializedName("active_sec")
    @Expose
    int active_sec;

    @SerializedName("steps")
    @Expose
    int steps;

    @SerializedName("type")
    @Expose
    int type;

    @SerializedName("time")
    @Expose
    String time;

    @Override
    public String toString() {
        return "StepsListItem{" +
                "id=" + id +
                ", active_sec=" + active_sec +
                ", steps=" + steps +
                ", type=" + type +
                ", time='" + time + '\'' +
                '}';
    }
}
