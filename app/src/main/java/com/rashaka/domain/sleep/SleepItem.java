package com.rashaka.domain.sleep;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Created by User on 13.11.2017.
 */

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SleepItem {

    @SerializedName("sleep_start")
    @Expose
    private String sleepStart;
    @SerializedName("sleep_end")
    @Expose
    private String sleepEnd;
    @SerializedName("description")
    @Expose
    private String description;
    @SerializedName("sleep_time")
    @Expose
    private String sleepTime;
    @SerializedName("date")
    @Expose
    private String date;

    @Override
    public String toString() {
        return "SleepItem{" +
                "sleepStart='" + sleepStart + '\'' +
                ", sleepEnd='" + sleepEnd + '\'' +
                ", description='" + description + '\'' +
                ", sleepTime='" + sleepTime + '\'' +
                ", date='" + date + '\'' +
                '}';
    }
}
