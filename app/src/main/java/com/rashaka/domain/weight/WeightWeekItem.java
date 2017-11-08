package com.rashaka.domain.weight;

/**
 * Created by User on 30.10.2017.
 */

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class WeightWeekItem {

    @SerializedName("year")
    @Expose
    private String year;

    @SerializedName("month")
    @Expose
    private String month;

    @SerializedName("day")
    @Expose
    private String day;

    @SerializedName("week_day")
    @Expose
    private String weekDay;

    @SerializedName("avg_weight")
    @Expose
    private String avgWeight;

    @Override
    public String toString() {
        return "WeightWeekItem{" +
                "year='" + year + '\'' +
                ", month='" + month + '\'' +
                ", day='" + day + '\'' +
                ", weekDay='" + weekDay + '\'' +
                ", avgWeight='" + avgWeight + '\'' +
                '}';
    }
}