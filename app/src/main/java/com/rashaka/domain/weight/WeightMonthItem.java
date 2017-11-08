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
public class WeightMonthItem {

    @SerializedName("start")
    @Expose
    private String start;

    @SerializedName("end")
    @Expose
    private String end;

    @SerializedName("avg_weight")
    @Expose
    private String avgWeight;

    @Override
    public String toString() {
        return "WeightMonthItem{" +
                "start='" + start + '\'' +
                ", end='" + end + '\'' +
                ", avgWeight='" + avgWeight + '\'' +
                '}';
    }
}