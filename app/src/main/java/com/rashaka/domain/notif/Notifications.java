package com.rashaka.domain.notif;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Created by User on 01.11.2017.
 */

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Notifications {

    @SerializedName("list")
    @Expose
    private List<NotificationItem> list;

    @Override
    public String toString() {
        return "Notifications{" +
                "list=" + list +
                '}';
    }
}
