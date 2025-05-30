package MiniProject.gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.net.*;

public class MainUI extends JFrame {
    private JButton encryptButton, decryptButton, transferButton, logoutButton;

    public MainUI() {
        setTitle("Secure File System");
        setSize(500, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(4, 1, 10, 10));
        
        
        
        JButton encryptButton = new JButton("Encrypt");
        encryptButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFileChooser fileChooser = new JFileChooser();
                int returnValue = fileChooser.showOpenDialog(null);

                if (returnValue == JFileChooser.APPROVE_OPTION) {
                    File selectedFile = fileChooser.getSelectedFile();

                    // Ask user for a secret key
                    String secretKey = JOptionPane.showInputDialog("Enter a secret key:");

                    if (secretKey != null && !secretKey.trim().isEmpty()) {
                        try {
                            File encryptedFile = new File(selectedFile.getParent(), selectedFile.getName() + ".enc");
                            CryptoUtils.encrypt(secretKey, selectedFile);
                            
                            

                        } catch (Exception ex) {
                            JOptionPane.showMessageDialog(null, "Encryption failed: " + ex.getMessage());
                        }
                    } else {
                        JOptionPane.showMessageDialog(null, "Secret key cannot be empty!");
                    }
                }
            }
        });

        JButton decryptButton = new JButton("Decrypt");
        decryptButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFileChooser fileChooser = new JFileChooser();
                int returnValue = fileChooser.showOpenDialog(null);

                if (returnValue == JFileChooser.APPROVE_OPTION) {
                    File selectedFile = fileChooser.getSelectedFile();

                    // Ask user for a secret key
                    String secretKey = JOptionPane.showInputDialog("Enter the secret key to decrypt:");

                    if (secretKey != null && !secretKey.trim().isEmpty()) {
                        try {
                            File decryptedFile = new File(selectedFile.getParent(), selectedFile.getName().replace(".enc", ""));
                            CryptoUtils.decrypt(secretKey, selectedFile);
                            
                            // Optionally return to home screen
                            

                        } catch (Exception ex) {
                            JOptionPane.showMessageDialog(null, "Decryption failed: " + ex.getMessage());
                        }
                    } else {
                        JOptionPane.showMessageDialog(null, "Secret key cannot be empty!");
                    }
                }
            }
        });

        
        
        transferButton = new JButton("Transfer File");
        logoutButton = new JButton("Logout");

        transferButton.addActionListener(e -> new TransferUI().setVisible(true));
        logoutButton.addActionListener(e -> {
            dispose();
            new LoginUI();
        });

        panel.add(encryptButton);
        panel.add(decryptButton);
        panel.add(transferButton);
        panel.add(logoutButton);

        add(panel);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new MainUI().setVisible(true));
    }
}

class TransferUI extends JFrame {
    private JButton sendButton, receiveButton;

    public TransferUI() {
        setTitle("File Transfer");
        setSize(400, 200);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(2, 1, 10, 10));

        sendButton = new JButton("Send File");
        receiveButton = new JButton("Receive File");

        sendButton.addActionListener(e -> sendFile());
        receiveButton.addActionListener(e -> receiveFile());

        panel.add(sendButton);
        panel.add(receiveButton);

        add(panel);
    }

    private void sendFile() {
        JFileChooser fileChooser = new JFileChooser();
        if (fileChooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
            File file = fileChooser.getSelectedFile();
            String ipAddress = JOptionPane.showInputDialog(this, "Enter receiver IP:");
            sendFileTo(file, ipAddress, 5000);
        }
    }

    private void receiveFile() {
        JOptionPane.showMessageDialog(this, "Waiting for sender...");
        receiveFileAt(5000);
    }

    private void sendFileTo(File file, String ip, int port) {
        try (Socket socket = new Socket(ip, port);
             FileInputStream fis = new FileInputStream(file);
             BufferedOutputStream bos = new BufferedOutputStream(socket.getOutputStream())) {

            byte[] buffer = new byte[4096];
            int bytesRead;
            while ((bytesRead = fis.read(buffer)) != -1) {
                bos.write(buffer, 0, bytesRead);
            }
            bos.flush();
            JOptionPane.showMessageDialog(this, "File sent successfully");
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "Error sending file: " + e.getMessage());
        }
    }

    private void receiveFileAt(int port) {
        try (ServerSocket serverSocket = new ServerSocket(port);
             Socket socket = serverSocket.accept();
             InputStream is = socket.getInputStream()) {

            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setDialogTitle("Save Received File");
            if (fileChooser.showSaveDialog(this) == JFileChooser.APPROVE_OPTION) {
                File file = fileChooser.getSelectedFile();
                try (FileOutputStream fos = new FileOutputStream(file);
                     BufferedOutputStream bos = new BufferedOutputStream(fos)) {

                    byte[] buffer = new byte[4096];
                    int bytesRead;
                    while ((bytesRead = is.read(buffer)) != -1) {
                        bos.write(buffer, 0, bytesRead);
                    }
                    bos.flush();
                    JOptionPane.showMessageDialog(this, "File received successfully");
                }
            }
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "Error receiving file: " + e.getMessage());
        }
    }
}
