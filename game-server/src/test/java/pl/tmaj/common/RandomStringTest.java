package pl.tmaj.common;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static pl.tmaj.common.RandomString.ofLength;

public class RandomStringTest {

    private static final int SOME_VALUE = 6;

    @Test
    public void shouldHaveGivenLength() {
        String string = ofLength(SOME_VALUE);
        assertEquals(string.length(), SOME_VALUE);
    }

    @Test
    public void shouldBeRandom() {
        String string1 = ofLength(SOME_VALUE);
        String string2 = ofLength(SOME_VALUE);
        assertFalse(string1.equals(string2));
    }
}
