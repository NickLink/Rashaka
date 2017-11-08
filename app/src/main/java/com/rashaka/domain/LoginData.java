package com.rashaka.domain;

/**
 * Created by User on 15.08.2017.
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
public class LoginData {

    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("tocken")
    @Expose
    private String tocken;
    @SerializedName("first_name")
    @Expose
    private String firstName;
    @SerializedName("last_name")
    @Expose
    private String lastName;
    @SerializedName("email")
    @Expose
    private String email;
    @SerializedName("image")
    @Expose
    private String image;
    @SerializedName("background")
    @Expose
    private String background;
    @SerializedName("height")
    @Expose
    private String height;
    @SerializedName("phone")
    @Expose
    private String phone;
    @SerializedName("sex")
    @Expose
    private String sex;
    @SerializedName("email_status")
    @Expose
    private String emailStatus;

    @Override
    public String toString() {
        return "LoginData{" +
                "id='" + id + '\'' +
                ", tocken='" + tocken + '\'' +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", email='" + email + '\'' +
                ", image='" + image + '\'' +
                ", background='" + background + '\'' +
                ", height='" + height + '\'' +
                ", phone='" + phone + '\'' +
                ", sex='" + sex + '\'' +
                ", emailStatus='" + emailStatus + '\'' +
                '}';
    }
}