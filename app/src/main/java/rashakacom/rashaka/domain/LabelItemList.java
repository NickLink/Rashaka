package rashakacom.rashaka.domain;

import java.util.ArrayList;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Created by User on 23.08.2017.
 */

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class LabelItemList {

    private ArrayList<LabelItem> data = new ArrayList<>();

    @Override
    public String toString() {
        return "LabelItemList{" +
                "data=" + data +
                '}';
    }
}
