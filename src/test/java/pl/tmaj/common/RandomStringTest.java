package pl.tmaj.common;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static pl.tmaj.common.RandomString.ofLength;

class RandomStringTest {

    private static final int SOME_VALUE = 6;

    @Test
    void shouldHaveGivenLength() {
        String string = ofLength(SOME_VALUE);
        assertEquals(string.length(), SOME_VALUE);
    }

    @Test
    void shouldBeRandom() {
        String string1 = ofLength(SOME_VALUE);
        String string2 = ofLength(SOME_VALUE);
        assertFalse(string1.equals(string2));
    }
}
