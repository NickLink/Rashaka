package com.rashaka.domain.sleep;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

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
public class Sleeps {

    @SerializedName("list")
    @Expose
    private List<SleepItem> list = new ArrayList<>();

    @Override
    public String toString() {
        return "Sleeps{" +
                "list=" + list +
                '}';
    }
}
