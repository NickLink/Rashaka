package com.rashaka.domain.routes;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Created by User on 24.10.2017.
 */

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PointInfo {

    @SerializedName("id")
    @Expose
    private long id;

    @SerializedName("desc")
    @Expose
    private String desc;

    @SerializedName("lat")
    @Expose
    private double lat;

    @SerializedName("lon")
    @Expose
    private double lon;

    @SerializedName("acc")
    @Expose
    private double acc;

    @SerializedName("time")
    @Expose
    private String time;

    @Override
    public String toString() {
        return "PointInfo{" +
                "id=" + id +
                ", desc='" + desc + '\'' +
                ", lat=" + lat +
                ", lon=" + lon +
                ", acc=" + acc +
                '}';
    }
}
