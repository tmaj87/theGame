package pl.tmaj.common;

import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class SHA256 {

    private static final String CHARSET_NAME = "UTF-8";

    public static String ofString(String input) {
        return ofString(input, RandomString.ofSize(64));
    }

    public static String ofString(String input, String salt) {
        try {
            byte[] byteInput = input.getBytes(CHARSET_NAME);
            byte[] byteSalt = salt.getBytes(CHARSET_NAME);
            return String.format("%064x", new BigInteger(1, getSHA256Raw(byteInput, byteSalt, 10_000)));
        } catch (NoSuchAlgorithmException | UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return "error";
    }

    private static byte[] getSHA256Raw(byte[] input, byte[] salt, int iterations) throws NoSuchAlgorithmException {
        if (iterations < 1) {
            return new byte[0];
        }
        MessageDigest digest = MessageDigest.getInstance("SHA-256");
        digest.reset();
        digest.update(salt);
        byte[] output = digest.digest(input);
        for (int i = 0; i < iterations; i++) {
            digest.reset();
            output = digest.digest(output);
        }
        return output;
    }
}
