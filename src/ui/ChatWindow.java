package ui;

import javax.swing.*;
import java.awt.*;

public class ChatWindow {
  private JPanel chatPanel;
  private JTextArea chatArea;

  private JPanel messagePanel;
  private JTextField messageField;
  private JButton sendButton;

  public ChatWindow(JFrame frame) {
    // JP Chat Panel
    chatPanel = new JPanel();

    chatArea = new JTextArea();
    chatArea.setEditable(false);
    chatArea.setLineWrap(true);
    chatArea.setWrapStyleWord(true);

    JScrollPane scrollPane = new JScrollPane(chatArea);
    chatPanel.add(scrollPane);
    frame.add(chatPanel, BorderLayout.CENTER);

    // JP Message Panel
    messagePanel = new JPanel();

    messageField = new JTextField(30);
    sendButton = new JButton("S E N D");
    sendButton.addActionListener(e -> sendMessage());

    messagePanel.add(messageField, BorderLayout.CENTER);
    messagePanel.add(sendButton, BorderLayout.EAST);
    frame.add(messagePanel, BorderLayout.SOUTH);
  }

  private void sendMessage() {
    String message = messageField.getText().trim();
    if(!message.isEmpty()) {
      displayMessage((message));
      messageField.setText("");
    }

    // TODO: Send Message to server for final version
  }

  private void displayMessage(String message) {
    // TODO: Improve this implementation with Message, Time, UserName etc...

    chatArea.append(message + '\n');
  }
}
