package com.rashaka.domain.database;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Created by User on 09.11.2017.
 */

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class DailyList {

    @SerializedName("list")
    @Expose
    List<DailyItem> list;

    @Override
    public String toString() {
        return "DailyList{" +
                "list=" + list +
                '}';
    }
}
