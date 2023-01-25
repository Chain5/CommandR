package it.emgtech.commandr.util;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class PasswordUtility {

    /**
     * Convert password into SHA-512 format.
     * @param passwordToHash
     *          Password to encrypt
     * @return encrypted password
     */
    public static String getSecurePassword(String passwordToHash) {

        String generatedPassword = null;
        try {
            // Tipo di codifica
            MessageDigest md = MessageDigest.getInstance("SHA-512");
            md.reset();
            byte[] bytes = md.digest(passwordToHash.getBytes());
            StringBuilder sb = new StringBuilder();

            for(int i=0; i< bytes.length ;i++)
                sb.append(Integer.toString((bytes[i] & 0xff) + 0x100, 16).substring(1));

            generatedPassword = sb.toString();

        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return generatedPassword;
    }

    /**
     * Verify password correctness
     * @param passwordIn
     *          user password
     * @param passwordHashed
     *          encrypted password
     * @return true if correct, false otherwise
     */
    public static boolean checkPassword(String passwordIn, String passwordHashed) {
        // encrypting user password with SHA-512
        final String passwordInHashed = getSecurePassword(passwordIn);

        if (passwordInHashed.equals(passwordHashed))
            return true;

        return false;
    }

    /**
     * Execute password validity controls
     * @param userPassword
     *          user password
     * @return return true if the password is valid, false otherwise
     */
    public static boolean checkPasswordValidity(String userPassword) {

        // Checking password length
        if (!((userPassword.length() >= 8))) {
            return false;
        }

        // Checking spaces
        if (userPassword.contains(" ")) {
            return false;
        }

        // Checking number presence
        int numberCounter = 0;
        for (int i = 0; i <= 9; i++) {
            // to convert int to string
            String str1 = Integer.toString(i);
            if (userPassword.contains(str1)) {
                numberCounter = 1;
            }
        }
        if (numberCounter == 0) {
            return false;
        }

        // Checking special characters presence
        if (!(userPassword.contains("@") || userPassword.contains("#")
                || userPassword.contains("!") || userPassword.contains("~")
                || userPassword.contains("$") || userPassword.contains("%")
                || userPassword.contains("^") || userPassword.contains("&")
                || userPassword.contains("*") || userPassword.contains("(")
                || userPassword.contains(")") || userPassword.contains("-")
                || userPassword.contains("+") || userPassword.contains("/")
                || userPassword.contains(":") || userPassword.contains(".")
                || userPassword.contains(", ") || userPassword.contains("<")
                || userPassword.contains(">") || userPassword.contains("?")
                || userPassword.contains("|"))) {
            return false;
        }

        // Checking uppercase letters presence
        int uppercaseLettersCounter = 0;
        for (int i = 65; i <= 90; i++) {
            char c = (char)i;
            String str1 = Character.toString(c);
            if (userPassword.contains(str1)) {
                uppercaseLettersCounter = 1;
            }
        }
        if (uppercaseLettersCounter == 0) {
            return false;
        }

        // Checking lowercase letters presence
        int lowercaseLettersCounter = 0;
        for (int i = 90; i <= 122; i++) {
            // type casting
            char c = (char)i;
            String str1 = Character.toString(c);
            if (userPassword.contains(str1)) {
                lowercaseLettersCounter = 1;
            }
        }
        if (lowercaseLettersCounter == 0) {
            return false;
        }

        // if its all correct...
        return true;
    }
}
