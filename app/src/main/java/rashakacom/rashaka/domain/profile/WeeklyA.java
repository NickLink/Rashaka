package rashakacom.rashaka.domain.profile;

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
public class WeeklyA {

    @SerializedName("av_steps")
    @Expose
    private Integer avSteps;
    @SerializedName("av_min")
    @Expose
    private Integer avMin;
    @SerializedName("av_steps_percent")
    @Expose
    private Integer avStepsPercent;
    @SerializedName("av_min_percent")
    @Expose
    private Integer avMinPercent;

    public WeeklyA(WeeklyA in){
        this(in.avSteps, in.avMin, in.avStepsPercent, in.avMinPercent);
    }

    @Override
    public String toString() {
        return "WeeklyA{" +
                "avSteps=" + avSteps +
                ", avMin=" + avMin +
                ", avStepsPercent=" + avStepsPercent +
                ", avMinPercent=" + avMinPercent +
                '}';
    }
}