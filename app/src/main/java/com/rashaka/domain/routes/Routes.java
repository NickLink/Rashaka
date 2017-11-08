package com.rashaka.domain.routes;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Created by User on 26.10.2017.
 */

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Routes {

    @SerializedName("list")
    @Expose
    List<RouteInfo> list;

    @Override
    public String toString() {
        return "Routes{" +
                "list=" + list +
                '}';
    }
}
