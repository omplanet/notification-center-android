package net.omplanet.notificationcenter.model;

import net.omplanet.notificationcenter.model.NotificationCenter;

public class NotificationEventHandler {
    public NotificationCenter defaulNotiftCenter;
    public NotificationCenter uiLayerNotifCenter;

    public NotificationEventHandler() {
        uiLayerNotifCenter = new NotificationCenter("uiLayerNotifCenter");
        defaulNotiftCenter = new NotificationCenter("defaulNotiftCenter");
        //If needed more layers of notification centers can be added here
    }

    public NotificationCenter getUiLayerNotifCenter() {
        return uiLayerNotifCenter;
    }

    public NotificationCenter getDefaultNotifCenter() {
        return defaulNotiftCenter;
    }

    public void handleMessageFromServer(String notification, Object object) {
        getUiLayerNotifCenter().postNotification(notification, object);
        getDefaultNotifCenter().postNotification(notification, object);
    }
}
