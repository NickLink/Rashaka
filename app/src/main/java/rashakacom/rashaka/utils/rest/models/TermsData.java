package rashakacom.rashaka.utils.rest.models;

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
public class TermsData {

    @SerializedName("title")
    @Expose
    private String title;

    @SerializedName("description")
    @Expose
    private String description;

    @Override
    public String toString() {
        return "TermsData{" +
                "title='" + title + '\'' +
                ", description='" + description + '\'' +
                '}';
    }
}