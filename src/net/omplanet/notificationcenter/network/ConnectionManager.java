package net.omplanet.notificationcenter.network;

import net.omplanet.network.asyncsocket.AsyncConnection;
import net.omplanet.network.asyncsocket.ConnectionHandler;
import net.omplanet.notificationcenter.model.AppEventHandler;
import net.omplanet.notificationcenter.model.AppManager;

import org.json.JSONException;
import org.json.JSONObject;

import android.util.Log;


public class ConnectionManager implements ConnectionHandler {
    private final String TAG = getClass().getName();

    public static final int SERVER_PORT = 1111;
    public static final String SERVER_URL = "www.example.com";

    public static final int SOCKET_TIMEOUT = 5000;
    public static final int NUMBER_OF_MAX_RETRIES_IN_CASE_OF_TIMEOUT = 4;
    public static final int HEARTBEAT_INTERVAL = 30000; //In milliseconds

    private AsyncConnection communicator;
    private AppEventHandler eventHandler;

    public enum ConnectionNotifications {
        SocketConnectedNotification,
        SocketConnectionFailedNotification,
        SocketDisconnectedNotification
    }

    public ConnectionManager() {
        eventHandler = AppManager.getInstance().getEventHandler();
    }

    public void connect() {
        communicator = new AsyncConnection(SERVER_URL, SERVER_PORT, SOCKET_TIMEOUT, this);
        communicator.execute();
    }

    public void disconnect() {
        if(communicator != null) {
            communicator.disconnect();
            communicator = null;
        }
    }

    public void sendMessage(String notification, Object object) throws Exception {
        if(communicator == null) {
            throw new Exception("The communicator instance is null.");
        }

        if(notification != null && object != null) {
            communicator.write(object.toString());

            eventHandler.getUiLayerNotifCenter().postNotification(notification, object);
            eventHandler.getDefaultNotifCenter().postNotification(notification, object);
        } else {
            throw new Exception("The sendMessage notification or object parameter is null.");
        }
    }

    @Override
    public void didReceiveData(String dataString) {
        try {
            JSONObject object = new JSONObject(dataString);
            String notification = object.getString("notification");
            Log.e(TAG, "didReceiveData: notification = " + notification);

            eventHandler.handleMessageFromServer(notification, object);
        } catch (JSONException ex) {
            Log.e(TAG, "didReceiveData(): error = " + ex);
        }

    }

    @Override
    public void didDisconnect(Exception error) {
        if(error == null) {
            eventHandler.getDefaultNotifCenter().postNotification(ConnectionNotifications.SocketDisconnectedNotification.toString(), null);
        } else {
            eventHandler.getDefaultNotifCenter().postNotification(ConnectionNotifications.SocketConnectionFailedNotification.toString(), error);
        }
    }

    @Override
    public void didConnect() {
        eventHandler.getDefaultNotifCenter().postNotification(ConnectionNotifications.SocketConnectedNotification.toString(), null);
    }
}
