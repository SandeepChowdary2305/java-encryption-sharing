# java-encryption-sharing
Java project for secure file encryption and local file sharing using AES and sockets.

## 🔐 Secure File Encryption, Decryption & Sharing System

A mini Java project that helps you **securely encrypt, decrypt, and share files** over a local network. Built using **Java Swing GUI + AES Encryption + Sockets**, this tool is perfect for teams or individuals who need to safely share files without exposing them to the internet.

---

## 🚀 What This Project Does

Before tools like this, people shared files using:
- Email (which is risky)
- USB drives (which are slow and unsafe)
- Manual handovers

This project makes file security **fast, simple, and local**:
- 🔒 Encrypt any file using AES-256
- 🔓 Decrypt only with the correct key
- 🌐 Send and receive files over LAN
- 🖥️ All wrapped in a clean Java GUI

---

## 📦 Project Structure

```
Secure-File-Encryption-System/
├── src/            # Java source code (Swing GUI, Encryption, File Transfer)
│   ├── gui/        # Java Swing forms
│   └── network/    # File transfer logic
├── build/          # NetBeans build output
├── nbproject/      # NetBeans project config
├── test/           # Test folder (optional)
├── LICENSE         # MIT License
├── README.md       # You're reading it
├── build.xml       # NetBeans build config
├── manifest.mf     # Manifest file for packaging
```

---

## 🛠️ Technologies Used

- Java (JDK 8+)
- Java Swing (UI)
- Java Sockets (LAN communication)
- AES Encryption (256-bit)

---

## 🎮 How to Run the Project

> 💡 Best used in NetBeans for easiest experience

1. **Open the Project** in NetBeans
2. Click **Run** → `Main.java`
3. Login.
4. Choose:
   - 🔐 Encrypt File
   - 🔓 Decrypt File
   - 📤 Send or 📥 Receive File over LAN

No database required. Everything runs locally and securely!

---

## 💡 Real-World Use Case: Healthcare File Security

This kind of system is ideal in places like hospitals where:
- Sensitive records are shared between systems
- Encryption is mandatory
- Network access is limited to internal LAN

Now, with this tool:
- Files are **encrypted**
- **LAN transfer** keeps data local
- System is easy to use by non-tech staff

---

## 👥 Project Members

- **L Sandeep** – Encryption & GUI
- **Hrushitha K L** – File Transfer (LAN)
- **K Vaishnav Rao** – Integration & Testing

---

## 📄 License

This project is licensed under the [MIT License](./LICENSE)

---

## 🙌 Acknowledgments

- Thanks to our faculty for guiding this mini-project.
- This project was made with teamwork and curiosity 🤝
