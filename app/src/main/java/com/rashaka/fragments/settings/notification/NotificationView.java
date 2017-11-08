package com.rashaka.fragments.settings.notification;

import com.rashaka.domain.notif.NotificationItem;

import java.util.List;

/**
 * Created by User on 24.08.2017.
 */

public interface NotificationView {

    void setLangValue();

    void setAdapterData(List<NotificationItem> list);
    void addAdapterData(List<NotificationItem> mData);
}
