package rashakacom.rashaka.utils.rest.models;

import com.google.gson.annotations.SerializedName;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Created by User on 25.07.2017.
 */

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class BaseResponse {

    @SerializedName("status")
    private Boolean status;

    @SerializedName("message")
    private String message;

    @Override
    public String toString() {
        return "BaseResponse{" +
                "status=" + status +
                ", message='" + message + '\'' +
                '}';
    }
}