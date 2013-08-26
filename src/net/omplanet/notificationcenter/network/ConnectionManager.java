package net.omplanet.notificationcenter.network;

import net.omplanet.network.asyncsocket.AsyncConnection;
import net.omplanet.network.asyncsocket.ConnectionHandler;
import net.omplanet.notificationcenter.DemoAppManager;
import net.omplanet.notificationcenter.model.NotificationEventHandler;

import org.json.JSONException;
import org.json.JSONObject;

import android.util.Log;


public class ConnectionManager implements ConnectionHandler {
    private final String TAG = getClass().getName();

    public static final int SERVER_PORT = 2222;
    public static final String SERVER_URL = "www.yoursocketserver.com";

    public static final int SOCKET_TIMEOUT = 5000;
    public static final int NUMBER_OF_MAX_RETRIES_IN_CASE_OF_TIMEOUT = 4;
    public static final int HEARTBEAT_INTERVAL = 30000; //In milliseconds

    private AsyncConnection communicator;
    private NotificationEventHandler eventHandler;

    public enum ConnectionNotifications {
        SocketConnectedNotification,
        SocketConnectionFailedNotification,
        SocketDisconnectedNotification
    }

    public ConnectionManager() {
        eventHandler = DemoAppManager.getInstance().getEventHandler();
    }

    public void connect() {
    	if(communicator == null) {
    		communicator = new AsyncConnection(SERVER_URL, SERVER_PORT, SOCKET_TIMEOUT, this);
    		communicator.execute();
    	}
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
            String notification = object.getString("notification");//The command name that server sends
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
        
        communicator = null;
    }

    @Override
    public void didConnect() {
        eventHandler.getDefaultNotifCenter().postNotification(ConnectionNotifications.SocketConnectedNotification.toString(), null);
    }
}
