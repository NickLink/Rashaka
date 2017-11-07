package com.rashaka.domain;

import com.google.gson.annotations.SerializedName;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Created by User on 12.09.2017.
 */

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RestPageResponse<T> {

    @SerializedName("status")
    private Boolean status;

    @SerializedName("next_page")
    private int next_page;

    @SerializedName("message")
    private String message;

    @SerializedName("data")
    private T mData;

    @Override
    public String toString() {
        return "RestPageResponse{" +
                "status=" + status +
                ", next_page=" + next_page +
                ", message='" + message + '\'' +
                ", mData=" + mData +
                '}';
    }
}
