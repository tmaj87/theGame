package pl.tmaj.common;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static pl.tmaj.common.RandomString.ofSize;

class RandomStringTest {

    private static final int SOME_VALUE = 6;

    @Test
    void shouldHaveGivenLength() {
        String string = ofSize(SOME_VALUE);
        assertEquals(string.length(), SOME_VALUE);
    }

    @Test
    void shouldBeRandom() {
        String string1 = ofSize(SOME_VALUE);
        String string2 = ofSize(SOME_VALUE);
        assertFalse(string1.equals(string2));
    }
}
