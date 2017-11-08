package com.rashaka.domain.news;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Created by User on 27.10.2017.
 */

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class News {

    @SerializedName("list")
    @Expose
    private List<NewsItem> list;

    @Override
    public String toString() {
        return "News{" +
                "list=" + list +
                '}';
    }
}
