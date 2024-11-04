package ui;

import javax.swing.*;
import java.awt.*;

public class InterfaceManager {
  private boolean sessionActive = false;
  private ChatWindow chatWindow;
  private LoginWindow loginWindow;
  private JFrame frame;

  public void loadRoom() {
    loginWindow = null;
    sessionActive = true;
    chatWindow = new ChatWindow(frame);
  }

  public void exitRoom() {
    chatWindow = null;
    sessionActive = false;
    loginWindow = new LoginWindow(frame);
  }

  public InterfaceManager() {
    frame = new JFrame();

    // JFrame Config
    frame.setTitle("LOOPED");
    frame.setSize(500,700);
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    frame.setLayout(new BorderLayout());
    frame.setResizable(false);

    exitRoom();
  }
}
