package rashakacom.rashaka.domain.fake_models;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by User on 07.09.2017.
 */

public class FakeDataSource {

    private static List<FakeNotification> list;

    public static List<FakeNotification> getFakeNotificationList(){

        list = new ArrayList<>();
        list.add(new FakeNotification("Title1", "Date1", "Time1", "I have an action-bar with an overflow menu. In that overflow menu i have text in each item. currently the items are align to left. And i want them to align to center in the overflow menu through XML"));
        list.add(new FakeNotification("Title2", "Date2", "Time2", "I have an action-bar with an overflow menu. In that overflow menu i have text in each item. currently the items are align to left. And i want them to align to center in the overflow menu through XML"));
        list.add(new FakeNotification("Title3", "Date3", "Time3", "I have an action-bar with an overflow menu. In that overflow menu i have text in each item. currently the items are align to left. And i want them to align to center in the overflow menu through XML"));
        list.add(new FakeNotification("Title4", "Date4", "Time4", "I have an action-bar with an overflow menu. In that overflow menu i have text in each item. currently the items are align to left. And i want them to align to center in the overflow menu through XML"));
        list.add(new FakeNotification("Title5", "Date5", "Time5", "I have an action-bar with an overflow menu. In that overflow menu i have text in each item. currently the items are align to left. And i want them to align to center in the overflow menu through XML"));
        list.add(new FakeNotification("Title6", "Date6", "Time6", "I have an action-bar with an overflow menu. In that overflow menu i have text in each item. currently the items are align to left. And i want them to align to center in the overflow menu through XML"));
        list.add(new FakeNotification("Title1", "Date1", "Time1", "I have an action-bar with an overflow menu. In that overflow menu i have text in each item. currently the items are align to left. And i want them to align to center in the overflow menu through XML"));
        list.add(new FakeNotification("Title2", "Date2", "Time2", "I have an action-bar with an overflow menu. In that overflow menu i have text in each item. currently the items are align to left. And i want them to align to center in the overflow menu through XML"));
        list.add(new FakeNotification("Title3", "Date3", "Time3", "I have an action-bar with an overflow menu. In that overflow menu i have text in each item. currently the items are align to left. And i want them to align to center in the overflow menu through XML"));
        list.add(new FakeNotification("Title4", "Date4", "Time4", "I have an action-bar with an overflow menu. In that overflow menu i have text in each item. currently the items are align to left. And i want them to align to center in the overflow menu through XML"));
        list.add(new FakeNotification("Title5", "Date5", "Time5", "I have an action-bar with an overflow menu. In that overflow menu i have text in each item. currently the items are align to left. And i want them to align to center in the overflow menu through XML"));
        list.add(new FakeNotification("Title6", "Date6", "Time6", "I have an action-bar with an overflow menu. In that overflow menu i have text in each item. currently the items are align to left. And i want them to align to center in the overflow menu through XML"));

        return list;
    }

}
