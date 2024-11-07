package network;

import models.Message;
import models.User;
import ui.InterfaceManager;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class ChatClient {
  private InterfaceManager ui;
  private User user;
  private Socket socket;
  private PrintWriter out;
  private BufferedReader in;

  public void start() {
    try {
      socket = new Socket(user.getIp(), Integer.parseInt(user.getPort()));
      in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
      out = new PrintWriter(socket.getOutputStream(), true);

      out.println(user.getUsername());

      new Thread(this::listenForMessage).start();
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }

  private void listenForMessage() {
    String message;
    while (true) {
      try {
        if (((message = in.readLine()) != null)) {
          System.out.println(message);
          String[] data = message.split(":", 2);
          Message msg = createMessage(data[1], data[0]);

          ui.displayMessage(msg);
        }
      } catch (IOException e) {
        throw new RuntimeException(e);
      }
    }
  }

  public void sendMessage(String message) {
    out.println(message);
  }

  public static void main(String[] args) {
    ChatClient chatClient = new ChatClient();
    chatClient.launchInterface();
  }

  private void launchInterface() {
    ui = new InterfaceManager();
    ui.setClient(this);
    ui.loginWindow();
  }

  public void serverLogin(String username, String port, String ip) {
    // TODO: If server return 1 switch ui else show error
    this.user = new User(username, ip, port);
    this.start();
    ui.chatWindow();
  }

  public void serverLogout() {
    ui.loginWindow();
    try {
      out.close();
      this.socket.close();
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }

  public Message createMessage(String message, String username) {
    return new Message(user.getUsername(), message);
  }
}
