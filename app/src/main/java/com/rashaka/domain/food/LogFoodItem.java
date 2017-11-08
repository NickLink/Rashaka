package com.rashaka.domain.food;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Created by User on 23.10.2017.
 */

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class LogFoodItem {

    @SerializedName("id")
    @Expose
    public String id;
    @SerializedName("user_id")
    @Expose
    public String userId;
    @SerializedName("description")
    @Expose
    public String description;
    @SerializedName("food_type")
    @Expose
    public String foodType;
    @SerializedName("date_time")
    @Expose
    public String dateTime;

    @Override
    public String toString() {
        return "LogFoodItem{" +
                "id='" + id + '\'' +
                ", userId='" + userId + '\'' +
                ", description='" + description + '\'' +
                ", foodType='" + foodType + '\'' +
                ", dateTime='" + dateTime + '\'' +
                '}';
    }
}