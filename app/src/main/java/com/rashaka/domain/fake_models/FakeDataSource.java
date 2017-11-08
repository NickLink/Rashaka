package com.rashaka.domain.fake_models;

import java.util.ArrayList;
import java.util.List;

import com.rashaka.domain.steps.StepsListItem;

/**
 * Created by User on 07.09.2017.
 */

public class FakeDataSource {

    private static List<FakeNotification> fakeNotifications;
    private static List<StepsListItem> stepsList;

    public static List<FakeNotification> getFakeNotificationList(){

        fakeNotifications = new ArrayList<>();
        fakeNotifications.add(new FakeNotification("Title1", "Date1", "Time1", "I have an action-bar with an overflow menu. In that overflow menu i have text in each item. currently the items are align to left. And i want them to align to center in the overflow menu through XML"));
        fakeNotifications.add(new FakeNotification("Title2", "Date2", "Time2", "I have an action-bar with an overflow menu. In that overflow menu i have text in each item. currently the items are align to left. And i want them to align to center in the overflow menu through XML"));
        fakeNotifications.add(new FakeNotification("Title3", "Date3", "Time3", "I have an action-bar with an overflow menu. In that overflow menu i have text in each item. currently the items are align to left. And i want them to align to center in the overflow menu through XML"));
        fakeNotifications.add(new FakeNotification("Title4", "Date4", "Time4", "I have an action-bar with an overflow menu. In that overflow menu i have text in each item. currently the items are align to left. And i want them to align to center in the overflow menu through XML"));
        fakeNotifications.add(new FakeNotification("Title5", "Date5", "Time5", "I have an action-bar with an overflow menu. In that overflow menu i have text in each item. currently the items are align to left. And i want them to align to center in the overflow menu through XML"));
        fakeNotifications.add(new FakeNotification("Title6", "Date6", "Time6", "I have an action-bar with an overflow menu. In that overflow menu i have text in each item. currently the items are align to left. And i want them to align to center in the overflow menu through XML"));
        fakeNotifications.add(new FakeNotification("Title1", "Date1", "Time1", "I have an action-bar with an overflow menu. In that overflow menu i have text in each item. currently the items are align to left. And i want them to align to center in the overflow menu through XML"));
        fakeNotifications.add(new FakeNotification("Title2", "Date2", "Time2", "I have an action-bar with an overflow menu. In that overflow menu i have text in each item. currently the items are align to left. And i want them to align to center in the overflow menu through XML"));
        fakeNotifications.add(new FakeNotification("Title3", "Date3", "Time3", "I have an action-bar with an overflow menu. In that overflow menu i have text in each item. currently the items are align to left. And i want them to align to center in the overflow menu through XML"));
        fakeNotifications.add(new FakeNotification("Title4", "Date4", "Time4", "I have an action-bar with an overflow menu. In that overflow menu i have text in each item. currently the items are align to left. And i want them to align to center in the overflow menu through XML"));
        fakeNotifications.add(new FakeNotification("Title5", "Date5", "Time5", "I have an action-bar with an overflow menu. In that overflow menu i have text in each item. currently the items are align to left. And i want them to align to center in the overflow menu through XML"));
        fakeNotifications.add(new FakeNotification("Title6", "Date6", "Time6", "I have an action-bar with an overflow menu. In that overflow menu i have text in each item. currently the items are align to left. And i want them to align to center in the overflow menu through XML"));

        return fakeNotifications;
    }

    public static List<StepsListItem> getStepsList(){
        stepsList = new ArrayList<>();
        stepsList.add(new StepsListItem(1, 500, 800, 1, "12:48:16"));
        stepsList.add(new StepsListItem(1, 700, 1200, 1, "12:48:16"));
        stepsList.add(new StepsListItem(1, 500, 800, 1, "12:48:16"));
        stepsList.add(new StepsListItem(1, 800, 1300, 1, "12:48:16"));
        stepsList.add(new StepsListItem(1, 500, 800, 1, "12:48:16"));
        stepsList.add(new StepsListItem(1, 900, 1400, 1, "12:48:16"));
        return stepsList;
    }

}
