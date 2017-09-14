package rashakacom.rashaka.domain;

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
public class UserData {

    @SerializedName("id")
    @Expose
    private String id;
    
    @SerializedName("email")
    @Expose
    private String email;
    
    @SerializedName("first_name")
    @Expose
    private String firstName;

    @SerializedName("last_name")
    @Expose
    private String lastName;

    @SerializedName("image")
    @Expose
    private String image;

    @SerializedName("background")
    @Expose
    private String background;

    @SerializedName("phone")
    @Expose
    private String phone;

    @SerializedName("birthday")
    @Expose
    private String birthday;

    @SerializedName("sex")
    @Expose
    private String sex;

    @SerializedName("hight")
    @Expose
    private String hight;

    @SerializedName("email_status")
    @Expose
    private String emailStatus;

    @SerializedName("weight")
    @Expose
    private String weight;

    @SerializedName("weight_goal")
    @Expose
    private String weightGoal;

    @SerializedName("steps_goal")
    @Expose
    private String stepsGoal;

    private transient long updated;


    @Override
    public String toString() {
        return "UserData{" +
                "id='" + id + '\'' +
                ", email='" + email + '\'' +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", image='" + image + '\'' +
                ", background='" + background + '\'' +
                ", phone='" + phone + '\'' +
                ", birthday='" + birthday + '\'' +
                ", sex='" + sex + '\'' +
                ", hight='" + hight + '\'' +
                ", emailStatus='" + emailStatus + '\'' +
                ", weight='" + weight + '\'' +
                ", weightGoal='" + weightGoal + '\'' +
                ", stepsGoal='" + stepsGoal + '\'' +
                '}';
    }
}