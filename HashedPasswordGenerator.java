// THIS PART OF THE PACKAGE WAS ADAPTED FROM LAB 5 SOLUTION

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

public class HashedPasswordGenerator {
    private static char[] salt;

    public HashedPasswordGenerator(char[] salt){
        this.salt = salt;
    }

    public String hashPassword(char[] password) {
        try {
            System.out.println("-----NEW HASH WITH");
            System.out.println("password: " + String.valueOf(password));
            System.out.println("salt    : " + String.valueOf(salt));
            // Create a MessageDigest instance for SHA-256
            MessageDigest md = MessageDigest.getInstance("SHA-256");

            // Concatenate the salt and password bytes
            byte[] saltedPasswordBytes = concatenateBytes(salt.toString().getBytes(), password.toString().getBytes());
            System.out.println("salted bytes: " + saltedPasswordBytes.toString());

            // Update the digest with the salted password bytes
            md.update(saltedPasswordBytes);

            // Get the hashed password bytes
            byte[] hashedPasswordBytes = md.digest();

            // Convert the hashed password bytes to a hexadecimal string
            StringBuilder hexStringBuilder = new StringBuilder();
            for (byte b : hashedPasswordBytes) {
                hexStringBuilder.append(String.format("%02x", b));
            }
            System.out.println("outputs : " + hexStringBuilder.toString());
            return hexStringBuilder.toString();

        } catch (NoSuchAlgorithmException e) {
            // Handle the exception, e.g., log it or throw a custom exception
            e.printStackTrace();
            return null;
        }
    }

    public static byte[] concatenateBytes(byte[] arr1, byte[] arr2) {
        byte[] combined = new byte[arr1.length + arr2.length];
        System.arraycopy(arr1, 0, combined, 0, arr1.length);
        System.arraycopy(arr2, 0, combined, arr1.length, arr2.length);
        return combined;
    }

    /*
    public static void main(String[] args) {
        char[] password = "asd@456".toCharArray();
        String hashedPassword = hashPassword(password);

        System.out.println("Original Password: " + String.valueOf(password));
        System.out.println("Hashed Password: " + hashedPassword);
    }
    */
}