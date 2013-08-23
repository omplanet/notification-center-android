package net.omplanet.notificationcenter.model;

import net.omplanet.notificationcenter.network.ConnectionManager;


public class AppManager {
    private static AppManager instance;
    private AppEventHandler eventHandler;
    private ConnectionManager connectionManager;

    public static AppManager getInstance() {
        if(instance == null) {
            instance = new AppManager();
        }
        return instance;
    }

    private AppManager() {
        instance = this;
        eventHandler = new AppEventHandler();
        connectionManager = new ConnectionManager();
    }

    public AppEventHandler getEventHandler() {
        return eventHandler;
    }

    public ConnectionManager getConnectionManager() {
        return connectionManager;
    }
}
