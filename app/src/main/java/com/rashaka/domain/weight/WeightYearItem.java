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
public class WeightYearItem {

    @SerializedName("year")
    @Expose
    private String year;

    @SerializedName("month")
    @Expose
    private String month;

    @SerializedName("avg_weight")
    @Expose
    private String avgWeight;

    @Override
    public String toString() {
        return "WeightYearItem{" +
                "year='" + year + '\'' +
                ", month='" + month + '\'' +
                ", avgWeight='" + avgWeight + '\'' +
                '}';
    }
}