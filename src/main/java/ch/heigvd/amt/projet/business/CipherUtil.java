package ch.heigvd.amt.projet.business;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class CipherUtil {
    private final static char[] hexArray = "0123456789ABCDEF".toCharArray();

    public static String sha2Generator(String password) throws NoSuchAlgorithmException {
        MessageDigest sha256 = MessageDigest.getInstance("SHA-256");
        byte[] passwordBytes = password.getBytes();
        byte[] digest = sha256.digest(passwordBytes);
        return byteArrayToTokenString(digest);
    }

    public static String byteArrayToTokenString(byte[] bytes) {
        char[] hexChars = new char[bytes.length * 2];
        for ( int j = 0; j < bytes.length; j++ ) {
            int v = bytes[j] & 0xFF;
            hexChars[j * 2] = hexArray[v >>> 4];
            hexChars[j * 2 + 1] = hexArray[v & 0x0F];
        }
        return new String(hexChars);
    }

}
