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
public class DailyItem {

    private transient long id;

    @SerializedName("steps")
    @Expose
    private int steps;

    @SerializedName("date")
    @Expose
    private String date;

    @SerializedName("activities")
    @Expose
    private String activities;

    @SerializedName("sync")
    @Expose
    private int sync;

    @Override
    public String toString() {
        return "DailyItem{" +
                "id=" + id +
                ", steps=" + steps +
                ", date='" + date + '\'' +
                ", sync=" + sync +
                '}';
    }
}
