package ui;

import models.Message;
import network.ChatClient;

import javax.swing.*;
import java.awt.*;

public class ChatWindow {
  private JPanel windowPanel;
  private ChatClient client;

  private JPanel topPanel;
  private JLabel logoLabel;
  private ImageIcon logo;
  private JButton logoutButton;

  private JPanel chatPanel;
  private JPanel chatArea;

  private JPanel messagePanel;
  private JTextField messageField;
  private JButton sendButton;

  public ChatWindow(JFrame frame, ChatClient client) {
    this.client = client;
    frame.getContentPane().removeAll();

    GridBagConstraints gbc = new GridBagConstraints();
    gbc.fill = GridBagConstraints.HORIZONTAL;

    windowPanel = new JPanel();
    windowPanel.setLayout(new GridBagLayout());

    // Top Panel
    topPanel = new JPanel();

    logoLabel = new JLabel();
    logo = new ImageIcon("appLogo.png");
    Image image = logo.getImage();
    image = image.getScaledInstance(90,90,Image.SCALE_SMOOTH);
    logo.setImage(image);
    logoLabel.setIcon(logo);

    logoLabel.setVerticalAlignment(SwingConstants.CENTER);
    logoLabel.setHorizontalAlignment(SwingConstants.LEFT);

    logoutButton = new JButton("#");
    logoutButton.addActionListener(e -> {
      client.serverLogout();
    });

    topPanel.add(logoLabel);
    topPanel.add(Box.createHorizontalStrut(300));
    topPanel.add(logoutButton);

    gbc.gridx = 0;
    gbc.gridy = 0;
    gbc.weighty = 0.001;

    windowPanel.add(topPanel, gbc);

    // Separator Line For Top Panel
    JSeparator separatorLine = new JSeparator(SwingConstants.HORIZONTAL);
    separatorLine.setPreferredSize(new Dimension(50,5));

    gbc.gridx = 0;
    gbc.gridy = 1;
    gbc.weighty = 0.001;

    windowPanel.add(separatorLine, gbc);

    // JP Chat Panel
    chatPanel = new JPanel();
    chatArea = new JPanel();
    chatArea.setLayout(new BoxLayout(chatArea, BoxLayout.Y_AXIS));

    for(int i = 0; i < 30; i++) {
      chatArea.add(new JPanel());
    }

    JScrollPane scrollPane = new JScrollPane(chatArea);
    scrollPane.setPreferredSize(new Dimension(420,480));
    scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);

    chatPanel.add(scrollPane);

    gbc.gridx = 0;
    gbc.gridy = 2;
    gbc.weighty = 1;

    windowPanel.add(chatPanel, gbc);

    // JP Message Panel
    messagePanel = new JPanel();

    messageField = new JTextField(25);
    messageField.setFont(new Font("SansSerif", Font.PLAIN, 18));

    sendButton = new JButton("S E N D");
    sendButton.addActionListener(e -> {
      sendMessage();
      frame.revalidate();
      frame.repaint();
    });

    messagePanel.add(messageField, BorderLayout.CENTER);
    messagePanel.add(sendButton, BorderLayout.EAST);

    gbc.gridx = 0;
    gbc.gridy = 3;
    gbc.weighty = 1;

    windowPanel.add(messagePanel, gbc);


    // Final Frame
    frame.add(windowPanel);
    frame.setVisible(true);
  }

  private void sendMessage() {
    String message = messageField.getText().trim();
    if(!message.isEmpty()) {
      client.sendMessage(message);
      messageField.setText("");
    }

    // TODO: Send Message to server for final version
  }

  public void displayMessage(Message message) {
    // TODO: Improve this implementation with Message, Time, UserName etc...

    JPanel msgPanel = new JPanel();
    JTextArea msg = new JTextArea(1,30);
    msg.setLineWrap(true);
    msg.setWrapStyleWord(true);
    msg.setEditable(false);

    msg.setText(message.getMessageContent() + '\n' + message.getSender());
    msgPanel.add(msg);

    chatArea.add(msgPanel);
  }
}
