package network;

import ui.InterfaceManager;

public class ChatClient {
  static InterfaceManager ui;

  public static void main(String[] args) {
    ui = new InterfaceManager();
  }

  public static void serverLogin(String username, String port, String ip) {
    // TODO: If server return 1 switch ui else show error
    ui.loadRoom();
  }

  public static void serverLogout() {
    ui.exitRoom();
  }
}
