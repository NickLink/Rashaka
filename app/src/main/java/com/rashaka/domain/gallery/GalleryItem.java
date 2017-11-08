package com.rashaka.domain.gallery;

/**
 * Created by User on 27.10.2017.
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
public class GalleryItem {

    @SerializedName("id")
    @Expose
    private String id;

    @SerializedName("title")
    @Expose
    private String title;

    @SerializedName("description")
    @Expose
    private String description;

    @SerializedName("counter")
    @Expose
    private Integer counter;

    @SerializedName("image")
    @Expose
    private String image;

    @SerializedName("video")
    @Expose
    private String video;

    @SerializedName("type")
    @Expose
    private String type;

    @Override
    public String toString() {
        return "GalleryItem{" +
                "id='" + id + '\'' +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", counter=" + counter +
                ", image='" + image + '\'' +
                ", video='" + video + '\'' +
                ", type='" + type + '\'' +
                '}';
    }
}