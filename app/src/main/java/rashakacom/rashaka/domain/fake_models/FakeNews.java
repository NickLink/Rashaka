package rashakacom.rashaka.domain.fake_models;

/**
 * Created by User on 28.08.2017.
 */

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class FakeNews {

    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("source")
    @Expose
    private String source;
    @SerializedName("sortBy")
    @Expose
    private String sortBy;
    @SerializedName("articles")
    @Expose
    private List<Article> articles = new ArrayList<Article>();

    @Override
    public String toString() {
        return "FakeNews{" +
                "status='" + status + '\'' +
                ", source='" + source + '\'' +
                ", sortBy='" + sortBy + '\'' +
                ", articles=" + articles +
                '}';
    }
}
