package com.rashaka.domain.bmi;

/**
 * Created by User on 31.10.2017.
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
public class BmiItem {

    @SerializedName("year")
    @Expose
    private String year;

    @SerializedName("month")
    @Expose
    private String month;

    @SerializedName("avg_bmi")
    @Expose
    private String avgBmi;

    @Override
    public String toString() {
        return "BmiItem{" +
                "year='" + year + '\'' +
                ", month='" + month + '\'' +
                ", avgBmi='" + avgBmi + '\'' +
                '}';
    }
}