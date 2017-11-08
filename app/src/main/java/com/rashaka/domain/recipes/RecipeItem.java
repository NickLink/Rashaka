package com.rashaka.domain.recipes;

/**
 * Created by User on 30.10.2017.
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
public class RecipeItem {

    @SerializedName("id")
    @Expose
    private String id;

    @SerializedName("title")
    @Expose
    private String title;

    @SerializedName("recipt")
    @Expose
    private String receipt;

    @SerializedName("directions")
    @Expose
    private String directions;

    @SerializedName("image")
    @Expose
    private String image;

    @Override
    public String toString() {
        return "RecipeItem{" +
                "id='" + id + '\'' +
                ", title='" + title + '\'' +
                ", receipt='" + receipt + '\'' +
                ", directions='" + directions + '\'' +
                ", image='" + image + '\'' +
                '}';
    }
}