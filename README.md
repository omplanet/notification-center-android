notification-center-android
===========================

Android demo application that implements a notification center (similar to NSNotificationCenter in iOS). To demonstrate a complete asynchronous socket communication layer is implemented that sends/receives commands to/from a socket server via the notification center.

This NotificationCenter class is an extension of the observer pattern in the Android and it can be used to broadcast notifications with objects to multiple UI, Network or any other logical layer in the scope of the application and catch notifications with appropriated methods.

For each notification name a separate set of Observable objects is created. The Observers are registered to the Observable of the notification.


Reference to iOS NSNotificationCenter: https://developer.apple.com/library/mac/#documentation/Cocoa/Reference/Foundation/Classes/nsnotificationcenter_Class/Reference/Reference.html
