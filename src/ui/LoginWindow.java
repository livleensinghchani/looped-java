package ui;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.sql.ConnectionBuilder;

public class LoginWindow {
  private JLabel logoLabel;
  private ImageIcon logo;

  private JPanel userPanel;
  private JTextField userField;

  private JPanel addressPanel;
  private JTextField addressField;

  private JPanel portPanel;
  private JTextField portField;

  private JPanel connectPanel;
  private JButton connectButton;

  LoginWindow(JFrame frame) {
    connectPanel = new JPanel();
    connectPanel.setLayout(new GridBagLayout());

    GridBagConstraints gbc = new GridBagConstraints();
    gbc.fill = GridBagConstraints.HORIZONTAL;
    gbc.insets = new Insets(2,2,2,2);

    logoLabel = new JLabel();
    logo = new ImageIcon("appLogo.png");
    Image image = logo.getImage();
    image = image.getScaledInstance(300,300,Image.SCALE_SMOOTH);
    logo.setImage(image);
    logoLabel.setIcon(logo);

    logoLabel.setVerticalAlignment(SwingConstants.CENTER);
    logoLabel.setHorizontalAlignment(SwingConstants.CENTER);

    gbc.gridx = 0;
    gbc.gridy = 0;
    gbc.weighty = 0.3;
    connectPanel.add(logoLabel, gbc);

    // User Panel
    userPanel = createTitledPanel("U S E R");
    gbc.gridy = 1;
    gbc.weighty = 0.2;
    connectPanel.add(userPanel, gbc);

    // Address Panel
    addressPanel = createTitledPanel("I P");
    gbc.gridy = 2;
    gbc.weighty = 0.2;
    connectPanel.add(addressPanel, gbc);

    // Port Panel
    portPanel = createTitledPanel("P O R T");
    gbc.gridy = 3;
    gbc.weighty = 0.2;
    connectPanel.add(portPanel, gbc);

    // Connect Button
    connectButton = new JButton("C O N N E C T");
    connectButton.setFont(new Font("SansSerif", Font.BOLD, 18));

    connectButton.setBackground(Color.green);
    connectButton.setPreferredSize(new Dimension(200, 60));

    frame.add(connectPanel, BorderLayout.CENTER);
    frame.add(connectButton, BorderLayout.SOUTH);

    frame.setVisible(true);
  }

  private JPanel createTitledPanel(String name) {
    JPanel panel = new JPanel();
    Border line = BorderFactory.createTitledBorder(name);
    panel.setBorder(line);
    JTextField textField = new JTextField(25);
    textField.setFont(new Font("SansSerif", Font.PLAIN, 16));
    panel.add(textField, BorderLayout.CENTER);
    return panel;
  }
}
