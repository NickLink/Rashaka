package com.rashaka.domain.weight;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Created by User on 30.10.2017.
 */

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class WeightYear {

    @SerializedName("list")
    @Expose
    private List<WeightYearItem> list;

    @Override
    public String toString() {
        return "WeightWeek{" +
                "list=" + list +
                '}';
    }
}
