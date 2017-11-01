package com.rashaka.domain.login;

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
public class UserLogin {

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
    @SerializedName("phone")
    @Expose
    private String phone;
    @SerializedName("email_status")
    @Expose
    private String emailStatus;

    @Override
    public String toString() {
        return "UserLogin{" +
                "id='" + id + '\'' +
                ", tocken='" + tocken + '\'' +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", email='" + email + '\'' +
                ", phone='" + phone + '\'' +
                ", emailStatus='" + emailStatus + '\'' +
                '}';
    }
}