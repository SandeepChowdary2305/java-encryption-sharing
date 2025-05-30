package MiniProject.gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class LoginUI extends JFrame {
    private JTextField usernameField;
    private JPasswordField passwordField;
    private JButton loginButton, exitButton;

    public LoginUI() {
        setTitle("Secure Login");
        setSize(400, 250);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);

        JPanel panel = new JPanel();
        panel.setLayout(new GridBagLayout());
        panel.setBackground(Color.LIGHT_GRAY);
        GridBagConstraints gbc = new GridBagConstraints();

        JLabel titleLabel = new JLabel("User Login");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 18));
        gbc.gridx = 0; gbc.gridy = 0; gbc.gridwidth = 2;
        gbc.insets = new Insets(10, 10, 10, 10);
        panel.add(titleLabel, gbc);

        JLabel userLabel = new JLabel("Username:");
        gbc.gridx = 0; gbc.gridy = 1; gbc.gridwidth = 1;
        panel.add(userLabel, gbc);

        usernameField = new JTextField(15);
        gbc.gridx = 1; gbc.gridy = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        panel.add(usernameField, gbc);

        JLabel passLabel = new JLabel("Password:");
        gbc.gridx = 0; gbc.gridy = 2;
        panel.add(passLabel, gbc);
        gbc.weightx = 1;
        passwordField = new JPasswordField(15);
        gbc.gridx = 1; gbc.gridy = 2;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        
        panel.add(passwordField, gbc);

        loginButton = new JButton("Login");
        exitButton = new JButton("Exit");
        gbc.gridx = 0; gbc.gridy = 3;
        gbc.gridwidth = 2;
        panel.add(loginButton, gbc);
        
        gbc.gridy = 4;
        panel.add(exitButton, gbc);

        loginButton.addActionListener(new LoginAction());
        exitButton.addActionListener(e -> System.exit(0));

        add(panel);
        setVisible(true);
    }

    private class LoginAction implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String user = usernameField.getText();
            String pass = new String(passwordField.getPassword());
            
            if (user.equals("user") && pass.equals("admin")) {
                JOptionPane.showMessageDialog(LoginUI.this, "Login Successful", "Success", JOptionPane.INFORMATION_MESSAGE);
                
                // ✅ Run MainUI after successful login
                new MainUI().setVisible(true);
                
                dispose(); // Close LoginUI
            } else {
                JOptionPane.showMessageDialog(LoginUI.this, "Invalid Credentials", "Error", JOptionPane.ERROR_MESSAGE);
            }
            usernameField.setText("");
            passwordField.setText("");
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(LoginUI::new);
    }
}
