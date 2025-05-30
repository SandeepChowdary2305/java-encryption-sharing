package MiniProject.networking;

import javax.swing.*;
import java.io.*;
import java.net.*;

public class FileReceiver {
    public void receiveFile() {
        new Thread(() -> {
            try {
                ServerSocket serverSocket = new ServerSocket(12345);
                JOptionPane.showMessageDialog(null, "Waiting for sender...");

                while (true) {
                    Socket socket = serverSocket.accept();
                    DataInputStream dis = new DataInputStream(socket.getInputStream());

                    String fileName = dis.readUTF();
                    long fileSize = dis.readLong();

                    File receivedFile = new File("Received_" + fileName);
                    FileOutputStream fos = new FileOutputStream(receivedFile);

                    byte[] buffer = new byte[4096];
                    int bytesRead;
                    while (fileSize > 0 && (bytesRead = dis.read(buffer, 0, (int) Math.min(buffer.length, fileSize))) != -1) {
                        fos.write(buffer, 0, bytesRead);
                        fileSize -= bytesRead;
                    }

                    fos.close();
                    dis.close();
                    socket.close();
                    JOptionPane.showMessageDialog(null, "File received: " + receivedFile.getName());
                }
            } catch (Exception e) {
                e.printStackTrace();
                JOptionPane.showMessageDialog(null, "Error receiving file!");
            }
        }).start();
    }
}
