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
public class Photo {

    @SerializedName("photo_id")
    @Expose
    private String photoId;
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
        return "Photo{" +
                "photoId='" + photoId + '\'' +
                ", image='" + image + '\'' +
                ", video='" + video + '\'' +
                ", type='" + type + '\'' +
                '}';
    }
}