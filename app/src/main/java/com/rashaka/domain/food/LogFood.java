package com.rashaka.domain.food;

/**
 * Created by User on 23.10.2017.
 */

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class LogFood {

    @SerializedName("list")
    @Expose
    public java.util.List<LogFoodItem> list = new ArrayList<>();

    @Override
    public String toString() {
        return "LogFood{" +
                "list=" + list +
                '}';
    }
}


