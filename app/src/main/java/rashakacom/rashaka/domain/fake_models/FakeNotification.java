package rashakacom.rashaka.domain.fake_models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Created by User on 07.09.2017.
 */

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class FakeNotification {

    private String title;

    private String date;

    private String time;

    private String text;

    @Override
    public String toString() {
        return "FakeNotification{" +
                "title='" + title + '\'' +
                ", date='" + date + '\'' +
                ", time='" + time + '\'' +
                ", text='" + text + '\'' +
                '}';
    }
}
