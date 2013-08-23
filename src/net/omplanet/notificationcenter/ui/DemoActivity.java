package net.omplanet.notificationcenter.ui;

import java.util.Observable;
import java.util.Observer;

import net.omplanet.notificationcenter.R;
import net.omplanet.notificationcenter.model.AppManager;
import net.omplanet.notificationcenter.model.NotificationHelper;
import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;

public class DemoActivity extends Activity {
    private final String TAG = getClass().getName();
    
    private AppManager serverManager;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_demo);
		
		serverManager = AppManager.getInstance();
		
		String notification = "GreeteingsFromClient";
		serverManager.getEventHandler().uiLayerNotifCenter.addObserver(notification, observerReceiveGreetingsFromServer);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.demo, menu);
		return true;
	}

	//Observers
    private Observer observerReceiveGreetingsFromServer = new Observer() {
        @Override
        public void update(Observable observable, final Object object) {
            new Thread() {
                @Override
                public void run() {
                    Log.d(TAG, "observerReceiveGreetingsFromServer() Greeting = " + object.toString());
                }
            }.start();
        }
    };
    
    public void connectButtonClicked(View view) {
    	serverManager.getConnectionManager().connect();
    }
    
    public void sayHelloButtonClicked(View view) {
    	try {
    		String notification = NotificationHelper.ClientNotifications.SayHelloToTheServerCommand.toString();
    		Object object = "Hello Server!";
			serverManager.getConnectionManager().sendMessage(notification, object);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
}
