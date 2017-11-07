package com.rashaka.domain;

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
public class RestResponse<T>  {

    @SerializedName("status")
    private Boolean status;

    @SerializedName("message")
    private String message;

    @SerializedName("data")
    private T mData;

    @Override
    public String toString() {
        return "RestResponse{" +
                "status=" + status +
                ", message='" + message + '\'' +
                ", mData=" + mData +
                '}';
    }
}
