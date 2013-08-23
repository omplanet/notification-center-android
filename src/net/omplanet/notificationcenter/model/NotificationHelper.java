package net.omplanet.notificationcenter.model;


public interface NotificationHelper {
	
    public enum ClientNotifications {
    	SayHelloToTheServerCommand,
    	SayGoodByeToTheServerCommand
    }
    
    public enum ServerNotifications {
    	HelloClientCommand
    }
}
