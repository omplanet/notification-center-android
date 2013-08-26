package net.omplanet.notificationcenter;

import net.omplanet.notificationcenter.model.NotificationEventHandler;
import net.omplanet.notificationcenter.network.ConnectionManager;


public class DemoAppManager {
    private static DemoAppManager instance;
    private NotificationEventHandler eventHandler;
    private ConnectionManager connectionManager;

    public static DemoAppManager getInstance() {
        if(instance == null) {
            instance = new DemoAppManager();
        }
        return instance;
    }

    private DemoAppManager() {
        instance = this;
        eventHandler = new NotificationEventHandler();
        connectionManager = new ConnectionManager();
    }

    public NotificationEventHandler getEventHandler() {
        return eventHandler;
    }

    public ConnectionManager getConnectionManager() {
        return connectionManager;
    }
}
