package net.omplanet.notificationcenter;

import java.util.Observable;
import java.util.Observer;

import net.omplanet.notificationcenter.model.NotificationCommandHelper;
import net.omplanet.notificationcenter.network.ConnectionManager;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.TextView;

public class DemoActivity extends Activity {
    private final String TAG = getClass().getName();
    private TextView commandLog;
    private DemoAppManager appManager;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_demo);
		
		//Init UI elements
		commandLog = (TextView) findViewById(R.id.textView_log);

        //Get application manager instance
		appManager = DemoAppManager.getInstance();

        //Initiate the observers
        String notification = ConnectionManager.ConnectionNotifications.SocketConnectedNotification.toString();
        appManager.getEventHandler().defaulNotiftCenter.addObserver(notification, observerSocketConnected);

        notification = ConnectionManager.ConnectionNotifications.SocketDisconnectedNotification.toString();
        appManager.getEventHandler().defaulNotiftCenter.addObserver(notification, observerSocketDisconnected);
        
        notification = ConnectionManager.ConnectionNotifications.SocketConnectionFailedNotification.toString();
        appManager.getEventHandler().defaulNotiftCenter.addObserver(notification, observerSocketConnectionFailed);

        notification = NotificationCommandHelper.ServerCommand.ServerCommand_1.toString();
		appManager.getEventHandler().uiLayerNotifCenter.addObserver(notification, observerServerCommand_1);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.demo, menu);
		return true;
	}

    //Button onClick handlers
    public void connectButtonClicked(View view) {
        appManager.getConnectionManager().connect();
    }

    public void disconnectButtonClicked(View view) {
        appManager.getConnectionManager().disconnect();
    }

    public void sendClientCommand_1Clicked(View view) {
        try {
            String notification = NotificationCommandHelper.ClientCommand.ClientCommand_1.toString();
            Object object = "Hello Server!";
            appManager.getConnectionManager().sendMessage(notification, object);
        } catch (Exception e) {
            Log.e(TAG, e.toString());
        }
    }

	//Observers
    private Observer observerSocketConnected = new Observer() {
        @Override
        public void update(Observable observable, final Object object) {
        	Log.d(TAG, "observerSocketConnected() received notifiaction");
            DemoActivity.this.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    
                    commandLog.setText("Socket connected.");
                }
            });
        }
    };

    private Observer observerSocketDisconnected = new Observer() {
        @Override
        public void update(Observable observable, final Object object) {
        	Log.d(TAG, "observerSocketDisconnected() received notifiaction.");
            DemoActivity.this.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    commandLog.setText("Socket disconnected.");
                }
            });
        }
    };
    
    private Observer observerSocketConnectionFailed = new Observer() {
        @Override
        public void update(Observable observable, final Object object) {
        	Log.d(TAG, "observerSocketConnectionFailed().");
            DemoActivity.this.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    commandLog.setText(object.toString());
                }
            });
        }
    };

    private Observer observerServerCommand_1 = new Observer() {
        @Override
        public void update(Observable observable, final Object object) {
        	Log.d(TAG, "observerServerCommand_1() Object = " + object.toString());
            DemoActivity.this.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    commandLog.setText(object.toString());
                }
            });
        }
    };

}
