package net.omplanet.notificationcenter.model;

import java.util.HashMap;
import java.util.Observable;
import java.util.Observer;

import android.util.Log;

public class NotificationCenter {
    private HashMap<String, CustomObservable> observables;
    private String name;

    private final String TAG = getClass().getName();

    public NotificationCenter(String name) {
    	this.name = name;
        this.observables = new HashMap<String, CustomObservable>();
    }

    public synchronized void addObserver(String notification, Observer observer) {
        CustomObservable observable = observables.get(notification);
        if (observable == null) {
            observable = new CustomObservable();
            observables.put(notification, observable);
        }

        observable.deleteObserver(observer); // To avoid adding duplicate observer objects
        observable.addObserver(observer);
    }

    public synchronized void removeObserver(String notification, Observer observer) {
        Observable observable = observables.get(notification);
        if (observable!=null) {
            observable.deleteObserver(observer);
        }
    }

    public synchronized void postNotification(String notification, Object object) {
        Log.d(TAG, name + ".postNotification(): notification = " + notification);
        
        //DEBUG print thread ID
        //Thread thread = Thread.currentThread();
        //Log.d(TAG, "postNotification: Current Thread ID = " + thread.getId());

        CustomObservable observable = observables.get(notification);
        if (observable != null) {
            observable.setChanged();
            observable.notifyObservers(object);
        }
    }

    //This class is created to make possible the access to protected setChanged() method.
    private class CustomObservable extends Observable {
        @Override
        protected void setChanged() {
            super.setChanged();
        }
    }
}
