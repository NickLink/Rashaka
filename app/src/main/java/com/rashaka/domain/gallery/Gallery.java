package com.rashaka.domain.gallery;

/**
 * Created by User on 27.10.2017.
 */

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Gallery {

    @SerializedName("list")
    @Expose
    private List<GalleryItem> list;

    @Override
    public String toString() {
        return "Gallery{" +
                "list=" + list +
                '}';
    }
}