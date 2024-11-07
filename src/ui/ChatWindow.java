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
  JScrollPane scrollPane;

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
    windowPanel.setBackground(Colors.DB);

    // Top Panel
    topPanel = new JPanel();
    topPanel.setBackground(Colors.DB);

    logoLabel = new JLabel();
    logo = new ImageIcon("assets/appLogo.png");
    Image logoScale = logo.getImage();
    logoScale = logoScale.getScaledInstance(90,90,Image.SCALE_SMOOTH);
    logo.setImage(logoScale);
    logoLabel.setIcon(logo);

    logoLabel.setVerticalAlignment(SwingConstants.CENTER);
    logoLabel.setHorizontalAlignment(SwingConstants.LEFT);

    logoutButton = new JButton();
    ImageIcon logoutIcon = new ImageIcon("assets/logout.png");
    Image logoutScale = logoutIcon.getImage();
    logoutScale = logoutScale.getScaledInstance(25,25,Image.SCALE_SMOOTH);
    logoutIcon.setImage(logoutScale);
    logoutButton.setIcon(logoutIcon);
    logoutButton.setBackground(Colors.TQ);

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
    separatorLine.setBackground(Colors.TQ);

    gbc.gridx = 0;
    gbc.gridy = 1;
    gbc.weighty = 0.001;

    windowPanel.add(separatorLine, gbc);

    // JP Chat Panel
    chatPanel = new JPanel();
    chatPanel.setBackground(Colors.DB);

    chatArea = new JPanel();
    chatArea.setBackground(Colors.DB);
    chatArea.setLayout(new BoxLayout(chatArea, BoxLayout.Y_AXIS));

    chatArea.add(Box.createVerticalStrut(400));

    scrollPane = new JScrollPane(chatArea);
    scrollPane.setBackground(Colors.DB);
    scrollPane.setPreferredSize(new Dimension(425,480));
    scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_NEVER);
    scrollPane.setBorder(BorderFactory.createEmptyBorder());

    chatPanel.add(scrollPane);

    gbc.gridx = 0;
    gbc.gridy = 2;
    gbc.weighty = 1;

    windowPanel.add(chatPanel, gbc);

    // JP Message Panel
    messagePanel = new JPanel();
    messagePanel.setBackground(Colors.DB);

    messageField = new JTextField(25);
    messageField.setFont(new Font("SansSerif", Font.PLAIN, 18));
    messageField.setBackground(Colors.DB);
    messageField.setForeground(Colors.CR);
    messageField.setPreferredSize(new Dimension(50,45));
    messageField.setBorder(BorderFactory.createLineBorder(Colors.TQ, 2));

    sendButton = new JButton();
    ImageIcon sendIcon = new ImageIcon("assets/message.png");
    Image sendScale = sendIcon.getImage();
    sendScale = sendScale.getScaledInstance(25,35,Image.SCALE_SMOOTH);
    sendIcon.setImage(sendScale);
    sendButton.setIcon(sendIcon);
    sendButton.setBackground(Colors.TQ);
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
  }

  public void displayMessage(Message message) {
    JPanel msgPanel = new JPanel();
    msgPanel.setBackground(Colors.DB);
    msgPanel.setLayout(new BorderLayout());

    JTextArea username = new JTextArea(1,25);
    username.setText(message.getSender());
    username.setBackground(Colors.DB);
    username.setFont(new Font("Monospace", Font.BOLD, 18));
    username.setForeground(Colors.TQ);
    username.setEditable(false);
    msgPanel.add(username, BorderLayout.NORTH);

    JPanel textArea = new JPanel();
    textArea.setBackground(Colors.DB);
    textArea.setLayout(new BorderLayout());

    JPanel vSeparator = new JPanel();
    vSeparator.setPreferredSize(new Dimension(4,0));
    vSeparator.setBackground(Colors.TQ);
    textArea.add(vSeparator, BorderLayout.WEST);

    JTextArea msg = new JTextArea(1,30);
    msg.setBackground(Colors.DB);
    msg.setForeground(Colors.CR);
    msg.setFont(new Font("Monospace", Font.PLAIN, 16));

    msg.setLineWrap(true);
    msg.setWrapStyleWord(true);
    msg.setEditable(false);

    msg.setText(message.getMessageContent());
    textArea.add(msg, BorderLayout.CENTER);

    msgPanel.add(textArea, BorderLayout.CENTER);

    if(!transientText(message)) {
      chatArea.add(msgPanel);
      addPadding();
    }

    JScrollBar verticalbar = scrollPane.getVerticalScrollBar();
    verticalbar.setValue(verticalbar.getMaximum()+5);

    scrollPane.repaint();
    scrollPane.revalidate();

    chatArea.repaint();
    chatArea.revalidate();
  }

  void addPadding() {
    JPanel panel = new JPanel();
    panel.setBackground(Colors.DB);
    panel.setPreferredSize(new Dimension(40,40));
    chatArea.add(panel);
  }

  boolean transientText(Message message) {
    int stackSize = chatArea.getComponentCount();

    if(stackSize-2 < 0) {
      return false;
    }

    JPanel targetMsg = (JPanel) chatArea.getComponent(stackSize-2);

    JTextArea username = (JTextArea) targetMsg.getComponent(0);
    JPanel textPanel = (JPanel) targetMsg.getComponent(1);
    JTextArea text = (JTextArea) textPanel.getComponent(1);

    if(username.getText().equals(message.getSender())) {
      text.append('\n'+message.getMessageContent());
    } else {
      return false;
    }
    return true;
  }
}
