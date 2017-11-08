package com.rashaka.domain.routes;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

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
public class RouteInfo {

    @SerializedName("id")
    @Expose
    private long id;

    @SerializedName("desc")
    @Expose
    private String desc;

    @SerializedName("start")
    @Expose
    private String start;

    @SerializedName("stop")
    @Expose
    private String stop;

    @SerializedName("time")
    @Expose
    private int time;

    @SerializedName("distance")
    @Expose
    private double distance;

    @SerializedName("pace")
    @Expose
    private double pace;

    @SerializedName("points")
    @Expose
    private List<PointInfo> points = new ArrayList<>();

    @Override
    public String toString() {
        return "RouteInfo{" +
                "id=" + id +
                ", desc='" + desc + '\'' +
                ", start='" + start + '\'' +
                ", stop='" + stop + '\'' +
                ", time='" + time + '\'' +
                ", distance=" + distance +
                ", pace=" + pace +
                ", points=" + points +
                '}';
    }
}
