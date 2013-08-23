package net.omplanet.notificationcenter.model;



public class AppEventHandler {
    public NotificationCenter defaultCenter;
    public NotificationCenter uiLayerNotifCenter;

    public AppEventHandler() {
        uiLayerNotifCenter = new NotificationCenter();
        defaultCenter = new NotificationCenter();
        //more notification centers can be created
    }

    public NotificationCenter getUiLayerNotifCenter() {
        return uiLayerNotifCenter;
    }

    public NotificationCenter getDefaultNotifCenter() {
        return defaultCenter;
    }

    public void handleMessageFromServer(String notification, Object object) {
        getUiLayerNotifCenter().postNotification(notification, object);
        getDefaultNotifCenter().postNotification(notification, object);
    }
}
