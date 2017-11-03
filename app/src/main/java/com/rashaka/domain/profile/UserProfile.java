package com.rashaka.domain.profile;

/**
 * Created by User on 07.09.2017.
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
public class UserProfile {

    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("email")
    @Expose
    private String email;
    @SerializedName("password")
    @Expose
    private String password;
    @SerializedName("first_name")
    @Expose
    private String firstName;
    @SerializedName("phone")
    @Expose
    private String phone;
    @SerializedName("last_name")
    @Expose
    private String lastName;
    @SerializedName("sex")
    @Expose
    private String sex;
    @SerializedName("image")
    @Expose
    private String image;
    @SerializedName("background")
    @Expose
    private String background;
    @SerializedName("birthday")
    @Expose
    private String birthday;
    @SerializedName("info")
    @Expose
    private String info;
    @SerializedName("hight")
    @Expose
    private String hight;
    @SerializedName("weight")
    @Expose
    private String weight;
    @SerializedName("weight_goal")
    @Expose
    private String weightGoal;
    @SerializedName("steps_goal")
    @Expose
    private String stepsGoal;
    @SerializedName("register_date")
    @Expose
    private String registerDate;
    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("tocken")
    @Expose
    private String tocken;
    @SerializedName("email_status")
    @Expose
    private String emailStatus;
    @SerializedName("get_push")
    @Expose
    private String getPush;
    @SerializedName("weekly_a")
    @Expose
    private WeeklyA weeklyA;

    public UserProfile(UserProfile in){
        this(in.id, in.email, in.password, in.firstName, in.phone, in.lastName,
                in.sex, in.image, in.background, in.birthday, in.info, in.hight, in.weight,
                in.weightGoal, in.stepsGoal, in.registerDate, in.status, in.tocken,
                in.emailStatus, in.getPush, in.weeklyA);
    }

    @Override
    public String toString() {
        return "UserProfile{" +
                "id='" + id + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", firstName='" + firstName + '\'' +
                ", phone='" + phone + '\'' +
                ", lastName='" + lastName + '\'' +
                ", sex='" + sex + '\'' +
                ", image='" + image + '\'' +
                ", background='" + background + '\'' +
                ", birthday='" + birthday + '\'' +
                ", info='" + info + '\'' +
                ", hight='" + hight + '\'' +
                ", weight='" + weight + '\'' +
                ", weightGoal='" + weightGoal + '\'' +
                ", stepsGoal='" + stepsGoal + '\'' +
                ", registerDate='" + registerDate + '\'' +
                ", status='" + status + '\'' +
                ", tocken='" + tocken + '\'' +
                ", emailStatus='" + emailStatus + '\'' +
                ", getPush='" + getPush + '\'' +
                ", weeklyA=" + weeklyA +
                '}';
    }
}