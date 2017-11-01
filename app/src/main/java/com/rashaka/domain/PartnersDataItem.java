package com.rashaka.domain;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Created by User on 15.08.2017.
 */

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PartnersDataItem {

    @SerializedName("id")
    @Expose
    private String id;

    @SerializedName("image")
    @Expose
    private String image;

    @Override
    public String toString() {
        return "PartnersDataItem{" +
                "id='" + id + '\'' +
                ", image='" + image + '\'' +
                '}';
    }
}
