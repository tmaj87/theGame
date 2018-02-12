package pl.tmaj.common;

import java.util.Random;

public class RandomString {

    private static final int ALPHABET_LENGTH = 26;

    public static String ofLength(int size) {
        return ofLength(size, true);
    }

    public static String ofLength(int size, boolean withSpecials) {
        final Random random = new Random();
        StringBuilder randomString = new StringBuilder();
        if (size < 1) {
            return "";
        }
        while (randomString.length() < size) {
            switch (random.nextInt(4)) {
                case 0: // numbers
                    randomString.append(random.nextInt(10));
                    break;
                case 1: // small letters
                    randomString.append((char) (97 + random.nextInt(ALPHABET_LENGTH)));
                    break;
                case 2: // big letters
                    randomString.append((char) (65 + random.nextInt(ALPHABET_LENGTH)));
                    break;
                case 3: // special chars
                    randomString.append(addSpecialOrNone(withSpecials));
                    break;
            }
        }
        return randomString.toString();
    }

    private static String addSpecialOrNone(boolean isActive) {
        final String specialCharacters = "?!@.,():;*&^%$#|-_=+";
        final Random random = new Random();
        if (isActive) {
            int randomInt = random.nextInt(specialCharacters.length());
            char randomSpecialChar = specialCharacters.charAt(randomInt);
            return String.valueOf(randomSpecialChar);
        }
        return "";
    }
}
