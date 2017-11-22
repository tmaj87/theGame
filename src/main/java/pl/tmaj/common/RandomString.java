package pl.tmaj.common;

import java.util.Random;

public class RandomString {

    public static String ofSize(int size) {
        if (size < 1) {
            return "";
        }
        final String specialCharacters = "?!@.,():;*&^%$#|-_=+";
        StringBuilder randomString = new StringBuilder();
        Random random = new Random();
        while (randomString.length() < size) {
            switch (random.nextInt(4)) {
                case 0: // numbers
                    randomString.append(random.nextInt(10));
                    break;
                case 1: // small letters
                    randomString.append((char) (97 + random.nextInt(26)));
                    break;
                case 2: // big letters
                    randomString.append((char) (65 + random.nextInt(26)));
                    break;
                case 3: // special chars
                    randomString.append(specialCharacters.charAt(random.nextInt(specialCharacters.length())));
                    break;
            }
        }
        return randomString.toString();
    }
}
