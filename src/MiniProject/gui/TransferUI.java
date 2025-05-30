package MiniProject.gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.net.*;

public class TransferUI extends JFrame {
    private JButton sendButton, receiveButton;

    public TransferUI() {
        setTitle("File Transfer");
        setSize(400, 200);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);

        JPanel panel = new JPanel();
        panel.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();

        JLabel titleLabel = new JLabel("Transfer File");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 18));
        gbc.gridx = 0; gbc.gridy = 0; gbc.gridwidth = 2;
        gbc.insets = new Insets(10, 10, 10, 10);
        panel.add(titleLabel, gbc);

        sendButton = new JButton("Send File");
        receiveButton = new JButton("Receive File");
        gbc.gridy = 1; gbc.gridwidth = 1;
        panel.add(sendButton, gbc);
        gbc.gridx = 1;
        panel.add(receiveButton, gbc);

        sendButton.addActionListener(e -> sendFile());
        receiveButton.addActionListener(e -> receiveFile());

        add(panel);
        setVisible(true);
    }

    private void sendFile() {
        JFileChooser fileChooser = new JFileChooser();
        int option = fileChooser.showOpenDialog(this);
        if (option == JFileChooser.APPROVE_OPTION) {
            File file = fileChooser.getSelectedFile();
            String ip = JOptionPane.showInputDialog(this, "Enter Receiver IP:");
            if (ip != null && !ip.isEmpty()) {
                new Thread(() -> transferFile(file, ip)).start();
            }
        }
    }

    private void transferFile(File file, String ip) {
        try (Socket socket = new Socket(ip, 5000);
             FileInputStream fis = new FileInputStream(file);
             BufferedOutputStream bos = new BufferedOutputStream(socket.getOutputStream())) {

            byte[] buffer = new byte[4096];
            int bytesRead;
            while ((bytesRead = fis.read(buffer)) != -1) {
                bos.write(buffer, 0, bytesRead);
            }
            bos.flush();
            JOptionPane.showMessageDialog(this, "File Sent Successfully!");
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "File Transfer Failed: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void receiveFile() {
        new Thread(() -> {
            try (ServerSocket serverSocket = new ServerSocket(5000);
                 Socket socket = serverSocket.accept();
                 BufferedInputStream bis = new BufferedInputStream(socket.getInputStream())) {

                JFileChooser fileChooser = new JFileChooser();
                fileChooser.setDialogTitle("Save Received File");
                int option = fileChooser.showSaveDialog(this);
                if (option == JFileChooser.APPROVE_OPTION) {
                    File file = fileChooser.getSelectedFile();
                    try (FileOutputStream fos = new FileOutputStream(file)) {
                        byte[] buffer = new byte[4096];
                        int bytesRead;
                        while ((bytesRead = bis.read(buffer)) != -1) {
                            fos.write(buffer, 0, bytesRead);
                        }
                        JOptionPane.showMessageDialog(this, "File Received Successfully!");
                    }
                }
            } catch (IOException e) {
                JOptionPane.showMessageDialog(this, "File Receive Failed: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        }).start();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(TransferUI::new);
    }
}
