package MiniProject.gui;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.io.*;
import java.nio.file.Files;
import java.security.SecureRandom;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class EncryptionModule {

    // Method to generate a secret key
    public static SecretKey generateKey() throws Exception {
        KeyGenerator keyGenerator = KeyGenerator.getInstance("AES");
        keyGenerator.init(256, new SecureRandom());
        return keyGenerator.generateKey();
    }

    // Method to encrypt a file
    public static void encryptFile(File inputFile, File outputFile, SecretKey secretKey) throws Exception {
        Cipher cipher = Cipher.getInstance("AES");
        cipher.init(Cipher.ENCRYPT_MODE, secretKey);
        
        byte[] fileBytes = Files.readAllBytes(inputFile.toPath());
        byte[] encryptedBytes = cipher.doFinal(fileBytes);
        Files.write(outputFile.toPath(), encryptedBytes);
    }

    // Encrypt button functionality integration
    public static void addEncryptFunctionality(JButton encryptButton, JFrame parentFrame) {
        encryptButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFileChooser fileChooser = new JFileChooser();
                int returnValue = fileChooser.showOpenDialog(parentFrame);
                
                if (returnValue == JFileChooser.APPROVE_OPTION) {
                    File selectedFile = fileChooser.getSelectedFile();
                    String keyInput = JOptionPane.showInputDialog(parentFrame, "Enter Secret Key:");
                    
                    if (keyInput != null && !keyInput.trim().isEmpty()) {
                        try {
                            SecretKey secretKey = new SecretKeySpec(keyInput.getBytes(), "AES");
                            JFileChooser saveChooser = new JFileChooser();
                            saveChooser.setDialogTitle("Save Encrypted File");
                            int saveOption = saveChooser.showSaveDialog(parentFrame);
                            
                            if (saveOption == JFileChooser.APPROVE_OPTION) {
                                File outputFile = saveChooser.getSelectedFile();
                                encryptFile(selectedFile, outputFile, secretKey);
                                JOptionPane.showMessageDialog(parentFrame, "File Encrypted Successfully!");
                            }
                        } catch (Exception ex) {
                            JOptionPane.showMessageDialog(parentFrame, "Encryption Failed: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                        }
                    }
                }
            }
        });
    }
}
