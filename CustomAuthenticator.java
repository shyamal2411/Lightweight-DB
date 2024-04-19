package customData;

import java.io.*;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Map;

public class CustomAuthenticator {
    private Map<String, String> usersData; // Map to store user data (username, hashed password)
    private String dataFilePath; // Path to the data file

    public CustomAuthenticator(String dataFilePath) {
        this.dataFilePath = dataFilePath;
        this.usersData = new HashMap<>();
        loadUserData();
    }

    private void loadUserData() {
        try (BufferedReader reader = new BufferedReader(new FileReader(dataFilePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split("::");
                if (parts.length == 2) {
                    usersData.put(parts[0], parts[1]);
                }
            }
        } catch (IOException e) {
            // Handle the exception (e.g., file not found).
        }
    }

    public void saveUserData() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(dataFilePath))) {
            for (Map.Entry<String, String> entry : usersData.entrySet()) {
                writer.write(entry.getKey() + "::" + entry.getValue());
                writer.newLine();
            }
        } catch (IOException e) {
            // Handle the exception (e.g., file write error).
        }
    }

    private String hashPassword(String password) throws NoSuchAlgorithmException {
        MessageDigest md = MessageDigest.getInstance("MD5");
        byte[] bytes = md.digest(password.getBytes());
        StringBuilder sb = new StringBuilder();
        for (byte aByte : bytes) {
            sb.append(Integer.toString((aByte & 0xff) + 0x100, 16).substring(1));
        }
        return sb.toString();
    }

    public boolean signUp(String username, String password) throws NoSuchAlgorithmException {
        if (usersData.containsKey(username)) {
            System.out.println("User already exists.");
            return false;
        }

        String hashedPassword = hashPassword(password);
        usersData.put(username, hashedPassword);
        saveUserData();
        return true;
    }

    public boolean login(String username, String password) throws NoSuchAlgorithmException {
        if (usersData.containsKey(username)) {
            String hashedPassword = hashPassword(password);
            if (hashedPassword.equals(usersData.get(username))) {
                System.out.println("Login successful.");
                return true;
            }
        }
        System.out.println("Login failed. Incorrect username or password.");
        return false;
    }

    public void logout(String username) {
        // Not implemented in this simplified version.
    }
}
