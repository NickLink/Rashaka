package rashakacom.rashaka.utils.rest.models;

import com.google.gson.annotations.Expose;
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
public class LabelItem {

    @SerializedName("key_word")
    @Expose
    private String keyWord;

    @SerializedName("title")
    @Expose
    private String title;

    @Override
    public String toString() {
        return "LabelItem{" +
                "keyWord='" + keyWord + '\'' +
                ", title='" + title + '\'' +
                '}';
    }
}
