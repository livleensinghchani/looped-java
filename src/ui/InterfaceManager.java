package ui;

import models.Message;
import network.ChatClient;

import javax.swing.*;
import java.awt.*;

public class InterfaceManager {
  private boolean sessionActive = false;
  private ChatWindow chatWindow;
  private LoginWindow loginWindow;
  private JFrame frame;
  private ChatClient client;

  public void chatWindow() {
    sessionActive = true;
    chatWindow = new ChatWindow(frame, client);
  }

  public void loginWindow() {
    sessionActive = false;
    loginWindow = new LoginWindow(frame, client);
  }

  public InterfaceManager(ChatClient client) {
    frame = new JFrame();

    // JFrame Config
    frame.setTitle("LOOPED");
    frame.setSize(500,700);
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    frame.setLayout(new BorderLayout());
    frame.setResizable(false);

    chatWindow();
  }

  public InterfaceManager() {
    frame = new JFrame();

    // JFrame Config
    frame.setTitle("LOOP");
    frame.setSize(500,700);
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    frame.setLayout(new BorderLayout());
    frame.setResizable(false);

    loginWindow();
  }

  public void setClient(ChatClient client) {
    this.client = client;
  }

  public void displayMessage(Message message) {
    chatWindow.displayMessage(message, frame);
    frame.repaint();
    frame.revalidate();
  }
}

