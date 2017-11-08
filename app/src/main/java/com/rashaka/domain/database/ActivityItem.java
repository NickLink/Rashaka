package com.rashaka.domain.database;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Created by User on 07.11.2017.
 */

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ActivityItem {

    private transient long id;

    @SerializedName("host")
    @Expose
    private String host;

    @SerializedName("sec")
    @Expose
    private int sec;

    @SerializedName("steps")
    @Expose
    private int steps;

    @SerializedName("type")
    @Expose
    private int type;

    @SerializedName("date")
    @Expose
    private String date;

    @Override
    public String toString() {
        return "ActivityItem{" +
                "id=" + id +
                ", host='" + host + '\'' +
                ", sec=" + sec +
                ", steps=" + steps +
                ", type=" + type +
                ", date='" + date + '\'' +
                '}';
    }
}
