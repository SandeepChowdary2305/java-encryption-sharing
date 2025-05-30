package MiniProject.gui;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.SecretKeySpec;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.IvParameterSpec;
import javax.swing.*;
import java.io.*;
import java.security.spec.KeySpec;
import javax.crypto.CipherOutputStream;

public class CryptoUtils {
    private static final String ALGORITHM = "AES";
    private static final String TRANSFORMATION = "AES/CBC/PKCS5Padding";
    private static final String SALT = "12345678";  // Change this for better security
    private static final byte[] IV = new byte[16];  // 16-byte IV for CBC mode

    // 🔹 Encrypt a file and let user choose where to save it
    public static void encrypt(String key, File inputFile) throws Exception {
        File outputFile = showSaveDialog("Save Encrypted File");
        if (outputFile != null) {
            doCrypto(Cipher.ENCRYPT_MODE, key, inputFile, outputFile);
            JOptionPane.showMessageDialog(null, "Encryption successful! File saved: " + outputFile.getAbsolutePath());
        } else {
            JOptionPane.showMessageDialog(null, "Encryption cancelled!");
        }
    }

    // 🔹 Decrypt a file and let user choose where to save it
    public static void decrypt(String key, File inputFile) throws Exception {
        File outputFile = showSaveDialog("Save Decrypted File");
        if (outputFile != null) {
            doCrypto(Cipher.DECRYPT_MODE, key, inputFile, outputFile);
            JOptionPane.showMessageDialog(null, "Decryption successful! File saved: " + outputFile.getAbsolutePath());
        } else {
            JOptionPane.showMessageDialog(null, "Decryption cancelled!");
        }
    }

    private static void doCrypto(int cipherMode, String key, File inputFile, File outputFile) throws Exception {
        SecretKey secretKey = generateKeyFromPassword(key);
        Cipher cipher = Cipher.getInstance(TRANSFORMATION);
        cipher.init(cipherMode, secretKey, new IvParameterSpec(IV));

        FileInputStream inputStream = new FileInputStream(inputFile);
        FileOutputStream outputStream = new FileOutputStream(outputFile);
        CipherOutputStream cipherOutputStream = new CipherOutputStream(outputStream, cipher);

        byte[] buffer = new byte[4096];
        int bytesRead;
        while ((bytesRead = inputStream.read(buffer)) != -1) {
            cipherOutputStream.write(buffer, 0, bytesRead);
        }

        cipherOutputStream.close();
        inputStream.close();
        outputStream.close();
    }

    // 🔹 Generates a SecretKey from a password
    private static SecretKey generateKeyFromPassword(String password) throws Exception {
        SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA256");
        KeySpec spec = new PBEKeySpec(password.toCharArray(), SALT.getBytes(), 65536, 256);
        SecretKey secretKey = factory.generateSecret(spec);
        return new SecretKeySpec(secretKey.getEncoded(), ALGORITHM);
    }

    // 🔹 Shows a save dialog so user can choose the output file location
    private static File showSaveDialog(String dialogTitle) {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle(dialogTitle);
        int userSelection = fileChooser.showSaveDialog(null);
        if (userSelection == JFileChooser.APPROVE_OPTION) {
            return fileChooser.getSelectedFile();
        }
        return null; // User canceled
    }
}
