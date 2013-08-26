package net.omplanet.notificationcenter.model;

public interface NotificationCommandHelper {
	
    public enum ClientCommand {
    	ClientCommand_1("client_cmd_1"),
        ClientCommand_2("client_cmd_2");
        //TODO add your commands here

        private String value;
        private ClientCommand(String value) {
            this.value = value;
        }

        @Override
        public String toString() {
            return value;
        }
    }
    
    public enum ServerCommand {
        ServerCommand_1("server_cmd_1"),
        ServerCommand_2("server_cmd_2");
        //TODO add your commands here

        private String value;
        private ServerCommand(String value) {
            this.value = value;
        }

        @Override
        public String toString() {
            return value;
        }
    }
}
