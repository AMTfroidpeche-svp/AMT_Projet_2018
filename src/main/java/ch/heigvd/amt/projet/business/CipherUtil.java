package ch.heigvd.amt.projet.business;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class CipherUtil {

    public static byte[] sha2Generator(String password) throws NoSuchAlgorithmException {
        MessageDigest sha256 = MessageDigest.getInstance("SHA-256");
        byte[] passwordBytes = password.getBytes();
        byte[] digest = sha256.digest(passwordBytes);
        return digest;
    }

}
