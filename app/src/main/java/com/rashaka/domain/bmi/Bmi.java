package com.rashaka.domain.bmi;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Created by User on 31.10.2017.
 */

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Bmi {

    @SerializedName("list")
    @Expose
    private List<BmiItem> list;

    @Override
    public String toString() {
        return "Bmi{" +
                "list=" + list +
                '}';
    }
}
